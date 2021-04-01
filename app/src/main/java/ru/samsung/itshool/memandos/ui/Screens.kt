package ru.samsung.itshool.memandos.ui

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.Fragments.DetailMemFragment
import ru.samsung.itshool.memandos.ui.Fragments.ProfileFragment
import ru.samsung.itshool.memandos.ui.Fragments.RibbonFragment
import ru.samsung.itshool.memandos.ui.Fragments.TabContainerFragment

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
}