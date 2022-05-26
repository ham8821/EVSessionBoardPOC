package com.example.evsessionboardpoc.data.model

import com.google.gson.annotations.SerializedName

data class Session(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("address") val address: String,
    @SerializedName("startTime") val startTime: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("charge") val charge: Double,
    @SerializedName("saving") val saving: Double,
    @SerializedName("cost") val cost: Double,
    @SerializedName("isCharged") val isCharged: Boolean,
    @SerializedName("additionalCharge") val additionalCharge: List<AdditionalCharge>
)

data class AdditionalCharge(
    @SerializedName("id") val id: Int,
    @SerializedName("info") val info: String,
    @SerializedName("cost") val cost: Double,
    @SerializedName("isCharged") val isCharged: Boolean
)
