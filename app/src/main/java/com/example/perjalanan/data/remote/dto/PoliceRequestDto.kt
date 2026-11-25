package com.example.perjalanan.data.remote.dto

import com.google.gson.annotations.SerializedName

// Request body untuk POST /police/request
data class PoliceRegistrationRequest(
    @SerializedName("polsekName") val polsekName: String,
    @SerializedName("fullName")   val fullName: String,
    @SerializedName("nrp")        val nrp: String,
    @SerializedName("phone")      val phone: String? = null,
    @SerializedName("reason")     val reason: String? = null
)

// Response dari POST /police/request
data class PoliceRegistrationResponse(
    @SerializedName("message")   val message: String,
    @SerializedName("requestId") val requestId: String
)

// Response dari GET /police/my-request
data class MyPoliceRequestResponse(
    @SerializedName("request") val request: PoliceRequestData?
)

data class PoliceRequestData(
    @SerializedName("id")          val id: String,
    @SerializedName("full_name")   val fullName: String,
    @SerializedName("nrp")         val nrp: String,
    @SerializedName("status")      val status: String,  // PENDING | APPROVED | REJECTED
    @SerializedName("created_at")  val createdAt: String,
    @SerializedName("polsek_name") val polsekName: String
)
