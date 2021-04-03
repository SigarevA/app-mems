package ru.samsung.itshool.memandos.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.samsung.itshool.memandos.model.repo.MemDatabase
import ru.samsung.itshool.memandos.ui.VM.ProfileVM

class ProfileVmFactory(
    private val memDatabase: MemDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileVM(memDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}