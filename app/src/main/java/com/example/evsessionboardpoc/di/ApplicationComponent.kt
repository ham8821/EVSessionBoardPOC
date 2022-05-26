package com.example.evsessionboardpoc.di

import com.example.evsessionboardpoc.EVApplication
import com.example.evsessionboardpoc.data.network.NetworkModule
import dagger.Component
import com.example.evsessionboardpoc.di.scopes.PerApp

@PerApp
@Component(modules = [ApplicationModule::class, NetworkModule::class])
interface ApplicationComponent {
    fun inject(application: EVApplication)
    fun plus(sessionsModule: SessionsModule): SessionsComponent
}