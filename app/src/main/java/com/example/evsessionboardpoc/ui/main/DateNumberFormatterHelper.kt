package com.example.evsessionboardpoc.ui.main

import java.text.SimpleDateFormat
import java.util.*

fun Date.dateToString(format: String): String {
    //simple date formatter
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    //return the formatted date string
    return dateFormatter.format(this)
}