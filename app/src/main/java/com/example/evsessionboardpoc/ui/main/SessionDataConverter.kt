package com.example.evsessionboardpoc.ui.main

import com.example.evsessionboardpoc.data.model.AdditionalCharge
import com.example.evsessionboardpoc.data.model.Session
import java.text.SimpleDateFormat
import java.util.*

fun List<Session>.groupDataIntoHashMap(): HashMap<String, List<Session>> {
    val groupedHashMap: HashMap<String, List<Session>> = HashMap()

    for (session in this) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val hashMapKey: String = dateFormatter.parse(session.date).dateToString("MMMM YYYY")
        if (groupedHashMap.containsKey(hashMapKey)) {
            // The key is already in the HashMap; add the pojo object
            // against the existing key.

            val list = groupedHashMap[hashMapKey]?.toMutableList()
            list?.add(session)
            groupedHashMap[hashMapKey] = list as List<Session>
        } else {
            // The key is not there in the HashMap; create a new key-value pair
            val list: MutableList<Session> = ArrayList()
            list.add(session)
            groupedHashMap[hashMapKey] = list
        }
    }

    return groupedHashMap
}