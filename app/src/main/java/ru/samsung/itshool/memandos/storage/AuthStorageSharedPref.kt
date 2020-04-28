package ru.samsung.itshool.memandos.storage

import android.content.Context
import ru.samsung.itshool.memandos.APP_ACCESS_TOKEN
import ru.samsung.itshool.memandos.utils.SharedPreferencesUtli

object AuthStorageSharedPref {

    fun saveAuthData(accessToken : String, context: Context) {
        SharedPreferencesUtli.insertData(context, APP_ACCESS_TOKEN, accessToken)
    }
}