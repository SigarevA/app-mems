package ru.samsung.itshool.memandos.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.fragments.*

object Screens {
   fun ribbon() = FragmentScreen {
       RibbonFragment()
   }

    fun detailMem(mem : Mem) = FragmentScreen {
        DetailMemFragment.getInstance(mem)
    }

    fun Profile() = FragmentScreen {
        ProfileFragment()
    }

    fun Tab(name : String) = FragmentScreen {
        TabContainerFragment.newInstance(name)
    }

    fun AddingMem() = FragmentScreen {
        AddingMemFragment()
    }
}