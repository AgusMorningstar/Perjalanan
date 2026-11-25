package com.example.perjalanan.data.remote

import com.example.perjalanan.data.remote.dto.MyPoliceRequestResponse
import com.example.perjalanan.data.remote.dto.PoliceRegistrationRequest
import com.example.perjalanan.data.remote.dto.PoliceRegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PoliceApiService {

    // User mengirim request pendaftaran polisi ke admin
    @POST("police/request")
    suspend fun submitPoliceRequest(
        @Body request: PoliceRegistrationRequest
    ): Response<PoliceRegistrationResponse>

    // User mengecek status request miliknya
    @GET("police/my-request")
    suspend fun getMyRequest(): Response<MyPoliceRequestResponse>
}
