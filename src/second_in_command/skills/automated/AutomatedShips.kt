package second_in_command.skills.automated

import com.fs.starfarer.api.Global
import com.fs.starfarer.api.campaign.AICoreOfficerPlugin
import com.fs.starfarer.api.campaign.CampaignFleetAPI
import com.fs.starfarer.api.campaign.FleetDataAPI
import com.fs.starfarer.api.combat.MutableShipStatsAPI
import com.fs.starfarer.api.combat.ShipAPI
import com.fs.starfarer.api.combat.ShipVariantAPI
import com.fs.starfarer.api.impl.campaign.AICoreOfficerPluginImpl
import com.fs.starfarer.api.impl.campaign.ids.Stats
import com.fs.starfarer.api.impl.campaign.ids.Strings
import com.fs.starfarer.api.impl.campaign.ids.Tags
import com.fs.starfarer.api.impl.hullmods.Automated
import com.fs.starfarer.api.ui.TooltipMakerAPI
import com.fs.starfarer.api.util.Misc
import second_in_command.SCData
import second_in_command.SCUtils
import second_in_command.misc.SCSettings
import second_in_command.misc.a9kSCSettings
import second_in_command.scripts.AutomatedShipsManager
import second_in_command.skills.technology.MakeshiftDrones
import second_in_command.specs.SCBaseSkillPlugin
import kotlin.math.roundToInt

class AutomatedShips : SCBaseAutoPointsSkillPlugin() {
    override fun getProvidedPoints(): Int {
        return 120
    }

    override fun addTooltip(data: SCData, tooltip: TooltipMakerAPI) {

        if (data.isNPC) {
            tooltip.addPara("Enables the fleet to field automated ships", 0f, Misc.getHighlightColor(), Misc.getHighlightColor())
            tooltip.addPara("Automated ships can only be captained by AI cores", 0f, Misc.getHighlightColor(), Misc.getHighlightColor())
            return
        }

        var manager = AutomatedShipsManager.get()
        var provided = getProvidedPoints() * a9kSCSettings.autoPoints
        var maximum = manager.getMaximumDP()
        var used = manager.getUsedDP()
        tooltip.addPara("+${provided.toInt()} automated ship points", 0f, Misc.getHighlightColor(), Misc.getHighlightColor())
        tooltip.addPara("Deployment points bonus from objectives is at least 10%% of the battle size, even if holding no objectives", 0f, Misc.getHighlightColor(), Misc.getHighlightColor())

        tooltip.addSpacer(10f)

        tooltip.addPara("Having more than zero automated ship points enables the recovery of automated ships", 0f, Misc.getHighlightColor(), Misc.getHighlightColor())
        tooltip.addPara("Automated ships can only be captained by AI cores", 0f, Misc.getHighlightColor(), Misc.getHighlightColor())

        tooltip.addSpacer(10f)

        if (!data.isSkillActive(spec.id)) {
            maximum += provided
        }

        var bonus = SCUtils.computeThresholdBonus(used, manager.MAX_CR_BONUS, maximum)

        tooltip.addPara("+${bonus.toInt()}%% combat readiness (maximum ${manager.MAX_CR_BONUS.toInt()}%%) - shared across similar skills - offsets built-in 100%% penalty", 0f, Misc.getHighlightColor(), Misc.getHighlightColor())

        tooltip.addPara("   - At maximum effectiveness while less than your total budget of automated ship points is used", 0f,
            Misc.getTextColor(), Misc.getHighlightColor(), )

        tooltip.addPara("   - Your maximum budget, including the amount provided by this skill, is at ${maximum.toInt()} automated ship points", 0f,
            Misc.getTextColor(), Misc.getHighlightColor(), "${maximum.toInt()}")

        tooltip.addPara("   - Your fleet currently uses ${used.toInt()} automated ship points", 0f,
            Misc.getTextColor(), Misc.getHighlightColor(), "${used.toInt()}")

        tooltip.addSpacer(10f)

        val alpha = AICoreOfficerPluginImpl.ALPHA_MULT.roundToInt()
        val beta = AICoreOfficerPluginImpl.BETA_MULT.roundToInt()
        val gamma = AICoreOfficerPluginImpl.GAMMA_MULT.roundToInt()
        var label = tooltip.addPara("" +
                "The total \"automated ship points\" are equal to the deployment points cost of " +
                "all automated ships in the fleet, with a multiplier for installed AI cores - " +
                "${alpha}${Strings.X} for an Alpha Core, " +
                "${beta}${Strings.X} for an Beta Core, " +
                "${gamma}${Strings.X} for a Gamma Core. " +
                "Due to safety interlocks, ships with AI cores do not contribute to the deployment point distribution.", 0f,
            Misc.getGrayColor(), Misc.getHighlightColor())

        label.setHighlight("${alpha}${Strings.X}", "${beta}${Strings.X}", "${gamma}${Strings.X}", "do not contribute to the deployment point distribution")
        label.setHighlightColors(Misc.getHighlightColor(), Misc.getHighlightColor(), Misc.getHighlightColor(), Misc.getNegativeHighlightColor())
    }

    override fun advance(data: SCData, amunt: Float?) {
        super.advance(data, amunt)

        if (data.isPlayer) {
            data.commander.stats.getDynamic().getMod(Stats.DEPLOYMENT_POINTS_MIN_FRACTION_OF_BATTLE_SIZE_BONUS_MOD)
                .modifyFlat(id, 0.1f)
        }


    }

    override fun onDeactivation(data: SCData) {
        super.onDeactivation(data)

        if (data.isPlayer) {
            data.commander.stats.getDynamic().getMod(Stats.DEPLOYMENT_POINTS_MIN_FRACTION_OF_BATTLE_SIZE_BONUS_MOD)
                .unmodify(id)
        }
    }
}