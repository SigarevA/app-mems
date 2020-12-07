package ru.samsung.itshool.memandos

import android.app.Application
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.di.component.DaggerAppComponent

class MemandosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ComponentHolder.appComponent = DaggerAppComponent.factory().create(this)
    }
}