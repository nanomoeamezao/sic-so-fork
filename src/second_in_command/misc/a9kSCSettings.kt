package second_in_command.misc

import com.fs.starfarer.api.Global
import lunalib.lunaSettings.LunaSettings
import lunalib.lunaSettings.LunaSettingsListener
import second_in_command.SCUtils

class a9kSCSettings : LunaSettingsListener {


    companion object {
        var MOD_ID = "second_in_command_addon_cheat"


        var maxLevel = LunaSettings.getInt(MOD_ID, "sc_officerMaxLevel")!!

        var crPenalty = LunaSettings.getFloat(MOD_ID, "sc_crPenalty")!!

        var autoPoints = LunaSettings.getFloat(MOD_ID, "sc_autoPoints")!!
        var maxNpcAptitudes = LunaSettings.getInt(MOD_ID, "sc_maxNpcAptitudes")!!
        var maxNpcSkills = LunaSettings.getInt(MOD_ID, "sc_maxNpcSkills")!!
        var xpPerLevel = listOf<Float>(
           /* 0f, //LV0
            75000f, //LV1
            150000f, //LV2
            300000f, //LV3
            600000f, //LV4*/

            0f, //LV0
            75000f, //LV1
            150000f, //LV2
            300000f, //LV3
            450000f, //LV4
            600000f, //LV5
            750000f, //LV6
            900000f, //LV7
            1050000f, //LV8
            1300000f, //LV9

          /*  0f, //LV0
            80000f, //LV1
            160000f, //LV2
            480000f, //LV3
            1440000f, //LV4*/
        )
    }

    init {
        applySettings()
    }

    override fun settingsChanged(modID: String) {
        /*if (modID == SCUtils.MOD_ID) {
            applySettings()
        }*/
        applySettings()
    }

    fun applySettings() {
        crPenalty = LunaSettings.getFloat(MOD_ID, "sc_crPenalty")!!
        maxLevel = LunaSettings.getInt(MOD_ID, "sc_officerMaxLevel")!!
        autoPoints = LunaSettings.getFloat(MOD_ID, "sc_autoPoints")!!
        maxNpcSkills = LunaSettings.getInt(MOD_ID, "sc_maxNpcSkills")!!
        maxNpcAptitudes = LunaSettings.getInt(MOD_ID, "sc_maxNpcAptitudes")!!
    }


}