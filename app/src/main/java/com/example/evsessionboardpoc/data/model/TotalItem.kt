package com.example.evsessionboardpoc.data.model

class TotalItem : ListItem() {
    var savings: Double = 0.00
    var cost: Double = 0.00
    override val type: Int
        get() = TYPE_TOTAL
}