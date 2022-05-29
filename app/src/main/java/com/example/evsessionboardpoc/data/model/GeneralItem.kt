package com.example.evsessionboardpoc.data.model

class GeneralItem : ListItem() {
    var session: Session? = null
    override val type: Int
        get() = TYPE_GENERAL
}