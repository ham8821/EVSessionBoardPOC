package com.example.evsessionboardpoc.data.model

abstract class ListItem {
    abstract val type: Int

    companion object {
        const val TYPE_DATE = 0
        const val TYPE_GENERAL = 1
        const val TYPE_TOTAL = 2
    }
}