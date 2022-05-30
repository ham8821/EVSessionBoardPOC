package com.example.evsessionboardpoc.ui.main

import com.example.evsessionboardpoc.data.model.Session
import java.util.HashMap

interface SessionsView {
    fun showLoading()
    fun showRefresh()

    fun showSessions(sessions: HashMap<String, List<Session>>)

    fun showLoadingError(message: String)
    fun showRefreshError(message: String)
}