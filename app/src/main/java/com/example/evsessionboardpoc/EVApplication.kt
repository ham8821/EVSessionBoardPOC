package com.example.evsessionboardpoc

import android.app.Application
import com.example.evsessionboardpoc.di.ApplicationComponent
import com.example.evsessionboardpoc.di.ApplicationModule
import com.example.evsessionboardpoc.di.DaggerApplicationComponent

class EVApplication: Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initDagger()
    }

    private fun initDagger() {
        applicationComponent =
            DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)
    }

    fun getComponent() = applicationComponent
}