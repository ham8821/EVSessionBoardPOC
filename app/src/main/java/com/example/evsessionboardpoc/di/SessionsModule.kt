package com.example.evsessionboardpoc.di

import com.example.evsessionboardpoc.data.network.SessionsClient
import com.example.evsessionboardpoc.data.repository.SessionsRepository
import dagger.Module
import dagger.Provides

@Module
class SessionsModule {
    @Provides
    fun provideSessionsRepository(sessionsClient: SessionsClient): SessionsRepository {
        return SessionsRepository(sessionsClient)
    }
}