package com.example.evsessionboardpoc.data.repository
import com.example.evsessionboardpoc.data.model.Session
import com.example.evsessionboardpoc.data.network.SessionsClient
import io.reactivex.Single
import javax.inject.Inject

class SessionsRepository @Inject constructor(
    private val sessionsClient: SessionsClient
) {
    private var sessionsCache: List<Session>? = null

    fun loadSessions(): Single<List<Session>> {
        val sessions = sessionsCache

        return if (sessions != null) {
            Single.just(sessions)
        } else {
            sessionsClient.getSessions().doOnSuccess { sessionsCache = it }
        }
    }

    fun refreshSessions(): Single<List<Session>> {
        return sessionsClient.getSessions().doOnSuccess { sessionsCache = it }
    }
}