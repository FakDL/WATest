package com.example.watest

import android.app.Application
import com.example.watest.di.AppComponent
import com.example.watest.di.DaggerAppComponent

class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
    }

    private fun initAppComponent(){
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }

}
