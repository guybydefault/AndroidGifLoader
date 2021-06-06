package ru.guybydefault.minin

import android.app.Application

class MyApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        appContainer = AppContainer()
        super.onCreate()
    }
}