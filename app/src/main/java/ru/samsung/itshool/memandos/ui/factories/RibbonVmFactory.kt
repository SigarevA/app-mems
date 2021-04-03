package ru.samsung.itshool.memandos.ui.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.samsung.itshool.memandos.model.repo.SurfMemesRepo
import ru.samsung.itshool.memandos.ui.VM.RibbonVM

class RibbonVmFactory(private val surfMemesRepo: SurfMemesRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RibbonVM::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RibbonVM(surfMemesRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}