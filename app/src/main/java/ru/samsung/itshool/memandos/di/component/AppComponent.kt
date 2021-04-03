package ru.samsung.itshool.memandos.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.samsung.itshool.memandos.di.modules.*
import ru.samsung.itshool.memandos.ui.Activites.LoginActivity
import ru.samsung.itshool.memandos.ui.VM.*
import ru.samsung.itshool.memandos.ui.fragments.*
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSchoolAPIModule::class,
        NetworkModule::class,
        RoomModule::class,
        NavigationModule::class,
        FactoryModule::class]
)
@Singleton
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
    fun inject(loginActivity: LoginActivity) : LoginActivity
    fun inject(profileFragment: ProfileFragment) : ProfileFragment
    fun inject(addingMemFragment: AddingMemFragment) : AddingMemFragment
    fun inject(tapeVM: TapeVM): TapeVM
    fun inject(addingMemVM: AddingMemVM): AddingMemVM
    fun inject(ribbonFragment: RibbonFragment): RibbonFragment
    fun inject(tabContainerFragment: TabContainerFragment): TabContainerFragment
    fun inject(detailMemFragment: DetailMemFragment): DetailMemFragment
}