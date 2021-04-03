package ru.samsung.itshool.memandos.storage

import ru.samsung.itshool.memandos.*
import ru.samsung.itshool.memandos.domain.UserInfo
import ru.samsung.itshool.memandos.utils.SharedPreferencesUtil
import javax.inject.Inject

class UserStorageSharedPref @Inject constructor(private val sharedPreferencesUtil: SharedPreferencesUtil) {
    fun saveUserData(user: UserInfo) {
        sharedPreferencesUtil.insertData(ID, user.id)
        sharedPreferencesUtil.insertData(NAME, user.username)
        sharedPreferencesUtil.insertData(FIRST_NAME, user.firstName)
        sharedPreferencesUtil.insertData(LAST_NAME, user.lastName)
        sharedPreferencesUtil.insertData(USER_DESCRIPTION, user.userDescription)
    }
}