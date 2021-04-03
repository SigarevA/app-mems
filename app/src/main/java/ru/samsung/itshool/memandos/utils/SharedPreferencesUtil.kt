package ru.samsung.itshool.memandos.utils

import android.content.Context
import android.content.SharedPreferences
import ru.samsung.itshool.memandos.APP_PREFERENCES

class SharedPreferencesUtil(private val context: Context) {

    private val pref : SharedPreferences
        get() = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    fun retriveData( key: String, default: String = "No data"): String? {
        return pref.getString(key, default)
    }

    fun insertData(key: String, value: String) {
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun insertData(key: String, value: Int) {
        val editor = pref.edit()
        editor.putInt(key, value)
        editor.apply()
    }
}