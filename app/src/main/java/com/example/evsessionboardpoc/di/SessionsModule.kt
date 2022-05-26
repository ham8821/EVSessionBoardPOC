package com.example.evsessionboardpoc.di

import android.content.Context
import com.example.evsessionboardpoc.data.network.SessionsClient
import com.example.evsessionboardpoc.data.repository.SessionsRepository
import com.example.evsessionboardpoc.di.qualifiers.ApplicationContext
import com.example.evsessionboardpoc.presenter.EVMessageProvider
import dagger.Module
import dagger.Provides

@Module
class SessionsModule {

    @Provides
    fun provideMessageProvider(@ApplicationContext context: Context): EVMessageProvider {
        return EVMessageProvider(context)
    }

    @Provides
    fun provideSessionsRepository(sessionsClient: SessionsClient): SessionsRepository {
        return SessionsRepository(sessionsClient)
    }
}