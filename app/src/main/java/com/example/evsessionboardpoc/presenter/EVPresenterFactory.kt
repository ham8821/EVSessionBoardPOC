package com.example.evsessionboardpoc.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.evsessionboardpoc.data.repository.SessionsRepository
import com.example.evsessionboardpoc.data.rx.AppSchedulers
import javax.inject.Inject
import javax.inject.Provider

class EVPresenterFactory @Inject constructor(
    private val repository: Provider<SessionsRepository>,
    private val schedulers: Provider<AppSchedulers>,
    private val messageProvider: Provider<EVMessageProvider>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EVPresenter(repository.get(), schedulers.get(), messageProvider.get()) as T
    }
}