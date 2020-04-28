package ru.samsung.itshool.memandos.storage

import android.content.Context
import ru.samsung.itshool.memandos.*
import ru.samsung.itshool.memandos.domain.UserInfo
import ru.samsung.itshool.memandos.utils.SharedPreferencesUtli

object UserStorageSharedPref {

    fun saveUserData(user : UserInfo, context: Context) {
        SharedPreferencesUtli.insertData(context, ID, user.id )
        SharedPreferencesUtli.insertData(context, NAME, user.username)
        SharedPreferencesUtli.insertData(context, FIRST_NAME, user.firstName)
        SharedPreferencesUtli.insertData(context, LAST_NAME, user.lastName)
        SharedPreferencesUtli.insertData(context, USER_DESCRIPTION, user.userDescription)
    }

}