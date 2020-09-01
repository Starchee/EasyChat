package com.starchee.easychat

import android.app.Application
import com.starchee.easychat.di.components.AppComponent
import com.starchee.easychat.di.components.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().withApplication(this).build()
    }
}