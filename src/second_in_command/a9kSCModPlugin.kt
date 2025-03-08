package second_in_command

import com.fs.starfarer.api.BaseModPlugin
import com.fs.starfarer.api.Global
import com.fs.starfarer.api.impl.campaign.ids.Abilities
import lunalib.lunaDebug.LunaDebug
import lunalib.lunaSettings.LunaSettings
import second_in_command.SCUtils.getPlayerData
import second_in_command.SCData
import second_in_command.misc.NPCFleetInflater
import second_in_command.misc.SCSettings
import second_in_command.misc.a9kSCSettings
import second_in_command.misc.snippets.AddAllOfficersSnippet
import second_in_command.misc.snippets.AddXPToOfficersSnippet
import second_in_command.scripts.*
import second_in_command.specs.SCOfficer
import second_in_command.specs.SCSpecStore
import java.lang.Exception

class a9kSCModPlugin : BaseModPlugin() {

    override fun onApplicationLoad() {
        LunaSettings.addSettingsListener(a9kSCSettings())
    }

    override fun onGameLoad(newGame: Boolean) {
        if (getPlayerData().getActiveOfficers().size <9) {
            getPlayerData().activeOfficers.add(null)
        }

            /* if (!Global.getSector().memoryWithoutUpdate.getBoolean("\$fourExecs")) {
                 activeOfficers.add(null)
                 Global.getSector().memoryWithoutUpdate.set("\$fourExecs", true)
             }*/
    }
}