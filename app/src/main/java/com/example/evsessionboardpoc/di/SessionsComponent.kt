package com.example.evsessionboardpoc.di

import com.example.evsessionboardpoc.ui.main.EvSessionsFragment
import dagger.Subcomponent

@Subcomponent(modules=[SessionsModule::class])
interface SessionsComponent{
    fun inject(fragment: EvSessionsFragment)
}