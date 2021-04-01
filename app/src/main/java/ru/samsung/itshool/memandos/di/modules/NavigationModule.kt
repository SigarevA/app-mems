package ru.samsung.itshool.memandos.di.modules

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import ru.samsung.itshool.memandos.subnavigation.LocalCiceroneHolder
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router

    @Provides
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<Router>) =
        cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideLocalCiceroneHolder() = LocalCiceroneHolder()
}