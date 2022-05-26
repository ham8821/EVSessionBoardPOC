package com.example.evsessionboardpoc.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import com.example.evsessionboardpoc.di.qualifiers.ApplicationContext
import com.example.evsessionboardpoc.di.scopes.PerApp
import com.example.evsessionboardpoc.data.rx.AppSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @PerApp
    fun provideAppSchedulers(): AppSchedulers =
        AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.computation(), Schedulers.io())
}