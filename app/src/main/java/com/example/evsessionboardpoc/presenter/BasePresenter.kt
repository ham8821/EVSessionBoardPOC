package com.example.evsessionboardpoc.presenter

import androidx.annotation.CallSuper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<View> : ViewModel(), LifecycleObserver {

    private var view: View? = null
    private var viewLifecycle: Lifecycle? = null
    private val disposable = CompositeDisposable()

    fun attachView(view: View, viewLifecycle: Lifecycle) {
        this.view = view
        this.viewLifecycle = viewLifecycle

        viewLifecycle.addObserver(this)
    }

    protected fun view(): View? {
        return view
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        view = null
        viewLifecycle = null
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    protected fun Disposable.autoClear() {
        disposable.add(this)
    }
}
