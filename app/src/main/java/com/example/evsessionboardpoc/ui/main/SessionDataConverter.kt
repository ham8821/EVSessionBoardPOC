package com.example.evsessionboardpoc.ui.main

import com.example.evsessionboardpoc.data.model.AdditionalCharge
import com.example.evsessionboardpoc.data.model.Session

class SessionDataConverter {
    val randomAddCharge =  ArrayList<AdditionalCharge>()
    val randomSession = Session(323, "sds","dfd","sdfsd","sdfsdf",45.12,23.00,23.02, true, randomAddCharge)
    val randomSessList = ArrayList<Session>()

    fun initMap(){
        var hashMap =  HashMap<String, List<Session>>()
        randomSessList.add(randomSession)
        hashMap["February 2022"] = randomSessList

    }

}