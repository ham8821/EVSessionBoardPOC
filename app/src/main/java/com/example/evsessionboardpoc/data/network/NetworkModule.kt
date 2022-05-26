package com.example.evsessionboardpoc.data.network

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import com.example.evsessionboardpoc.di.scopes.PerApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @PerApp
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = BODY
        return OkHttpClient.Builder().addInterceptor(logger).build()
    }

    @Provides
    @PerApp
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    @Provides
    @PerApp
    fun provideSessionClient(retrofit: Retrofit): SessionsClient = retrofit.create(SessionsClient::class.java)

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/ham8821/EVSessionBoardPOC/main/api/"
    }
}
