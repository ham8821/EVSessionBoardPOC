package com.example.evsessionboardpoc.data.model

class DateItem : ListItem() {
    var date: String? = null
    override val type: Int
        get() = TYPE_DATE
}