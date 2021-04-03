package ru.samsung.itshool.memandos.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.samsung.itshool.memandos.model.repo.SurfAuthorizationRepo
import ru.samsung.itshool.memandos.storage.UserStorageSharedPref
import ru.samsung.itshool.memandos.ui.VM.LoginVM

class LoginVmFactory(
    private val surfAuthRepo: SurfAuthorizationRepo,
    private val userStorageSharedPref: UserStorageSharedPref
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginVM(surfAuthRepo, userStorageSharedPref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}