package com.example.evsessionboardpoc.data.repository
import com.example.evsessionboardpoc.data.model.Session
import com.example.evsessionboardpoc.data.network.SessionsClient
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class SessionsRepository @Inject constructor(
    private val sessionsClient: SessionsClient
) {
    private var sessionsCache: List<Session>? = null

    fun loadSessions(): Single<List<Session>> {
        val sessions = sessionsCache

        if (sessions != null) {
            return Single.just(sessions)
        } else {
            return sessionsClient.getSessions().map {
                it.sortedByDescending { it.date }
//                val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
//                it.sortedByDescending { session -> dateFormatter.parse(session.date) }
            }.doOnSuccess {
                sessionsCache = it
            }
        }
    }

    fun refreshSessions(): Single<List<Session>> {
        return sessionsClient.getSessions().doOnSuccess { sessionsCache = it }
    }
}