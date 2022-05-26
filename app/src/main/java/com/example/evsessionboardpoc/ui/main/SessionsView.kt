package com.example.evsessionboardpoc.ui.main

import com.example.evsessionboardpoc.data.model.Session

interface SessionsView {
    fun showLoading()
    fun showRefresh()

    fun showSessions(sessions: List<Session>)

    fun showLoadingError(message: String)
    fun showRefreshError(message: String)
}