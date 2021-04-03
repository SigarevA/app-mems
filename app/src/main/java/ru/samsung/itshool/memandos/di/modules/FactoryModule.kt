package ru.samsung.itshool.memandos.di.modules

import dagger.Module
import dagger.Provides
import ru.samsung.itshool.memandos.model.repo.MemDatabase
import ru.samsung.itshool.memandos.model.repo.SurfAuthorizationRepo
import ru.samsung.itshool.memandos.model.repo.SurfMemesRepo
import ru.samsung.itshool.memandos.storage.UserStorageSharedPref
import ru.samsung.itshool.memandos.ui.factories.AddingMemVmFactory
import ru.samsung.itshool.memandos.ui.factories.LoginVmFactory
import ru.samsung.itshool.memandos.ui.factories.ProfileVmFactory
import ru.samsung.itshool.memandos.ui.factories.RibbonVmFactory
import javax.inject.Singleton

@Module
class FactoryModule {

    @Provides
    @Singleton
    fun providesRibbonVmFactory(surfMemesRepo: SurfMemesRepo): RibbonVmFactory =
        RibbonVmFactory(surfMemesRepo)

    @Provides
    @Singleton
    fun providesProfileVmFactory(memDatabase: MemDatabase): ProfileVmFactory =
        ProfileVmFactory(memDatabase)

    @Provides
    @Singleton
    fun providesLoginVmFactory(
        surfAuthRepo: SurfAuthorizationRepo, userStorageSharedPref: UserStorageSharedPref
    ): LoginVmFactory {
        return LoginVmFactory(surfAuthRepo, userStorageSharedPref)
    }

    @Provides
    @Singleton
    fun providesAddingMemVmFactory(
        memDatabase: MemDatabase
    ): AddingMemVmFactory {
        return AddingMemVmFactory(memDatabase)
    }
}