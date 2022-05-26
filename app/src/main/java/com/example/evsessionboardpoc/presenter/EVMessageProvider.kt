package com.example.evsessionboardpoc.presenter

import android.content.Context
import com.example.evsessionboardpoc.R

class EVMessageProvider(
    private val context: Context
) {

    fun getConnectionErrorMessage(): String {
        return context.getString(R.string.connection_error_occurred)
    }

    fun getUnknownErrorMessage(): String {
        return context.getString(R.string.unknown_error_occurred)
    }
}