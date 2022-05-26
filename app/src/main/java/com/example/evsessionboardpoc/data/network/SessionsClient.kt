package com.example.evsessionboardpoc.data.network

import com.example.evsessionboardpoc.data.model.Session
import io.reactivex.Single
import retrofit2.http.GET

interface SessionsClient {

    @GET("sessions")
    fun getSessions(): Single<List<Session>>
}