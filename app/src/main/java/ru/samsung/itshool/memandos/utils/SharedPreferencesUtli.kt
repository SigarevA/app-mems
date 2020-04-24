package ru.samsung.itshool.memandos.utils

import android.content.Context
import android.content.SharedPreferences
import ru.samsung.itshool.memandos.APP_PREFERENCES

class SharedPreferencesUtli {


    companion object {

        fun getPrefs(context: Context) : SharedPreferences {
            return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        }

        fun retriveData( context : Context, key : String, default : String = "No data") : String?
        {
            return getPrefs(context).getString(key, default)
        }

        fun insertData(context : Context, key  : String , value : String) {
            val editor = getPrefs(context).edit()
            editor.putString(key,value)
            editor.apply()
        }
    }

}