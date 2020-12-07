package ru.samsung.itshool.memandos.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.samsung.itshool.memandos.di.modules.AndroidSchoolAPIModule
import ru.samsung.itshool.memandos.di.modules.NetworkModule
import ru.samsung.itshool.memandos.di.modules.RoomModule
import ru.samsung.itshool.memandos.ui.VM.*
import javax.inject.Singleton

@Component(modules = [AndroidSchoolAPIModule::class, NetworkModule::class, RoomModule::class])
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(loginVM: LoginVM): LoginVM
    fun inject(robbionVM: RobbionVM): RobbionVM
    fun inject(tapeVM: TapeVM): TapeVM
    fun inject(profileVM: ProfileVM): ProfileVM
    fun inject(addingMemVM: AddingMemVM): AddingMemVM
}