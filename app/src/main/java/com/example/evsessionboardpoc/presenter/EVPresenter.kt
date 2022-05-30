package com.example.evsessionboardpoc.presenter

import android.util.Log
import com.example.evsessionboardpoc.data.repository.SessionsRepository
import com.example.evsessionboardpoc.data.rx.AppSchedulers
import com.example.evsessionboardpoc.ui.main.SessionsView
import com.example.evsessionboardpoc.ui.main.groupDataIntoHashMap
import java.io.IOException
import javax.inject.Inject

class EVPresenter @Inject constructor(
    private val repository: SessionsRepository,
    private val schedulers: AppSchedulers,
    private val messageProvider: EVMessageProvider
) : BasePresenter<SessionsView>() {

    fun loadSessions() {
        repository.loadSessions()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .doOnSubscribe {
//                view()?.showLoading()
            }
            .subscribe({
                view()?.showSessions(it.groupDataIntoHashMap())
            }, {
                Log.d("Throw", "loadSessions: "+it.message)
                view()?.showLoadingError(getErrorMessage(it))
            })
            .autoClear()
    }

    fun refreshSessions() {
        repository.refreshSessions()
            .subscribeOn(schedulers.io)
            .observeOn(schedulers.main)
            .subscribe({
                view()?.showSessions(it.groupDataIntoHashMap())
            }, {
                view()?.showRefreshError(getErrorMessage(it))
            })
            .autoClear()
    }

    fun retryRefreshClicked() {
        view()?.showRefresh()
        refreshSessions()
    }

    private fun getErrorMessage(error: Throwable): String {
        return when (error) {
            is IOException -> messageProvider.getConnectionErrorMessage()
            else -> messageProvider.getUnknownErrorMessage()
        }
    }
}