package com.example.myjournalapp.data.remote

import com.example.myjournalapp.data.model.RegistrationRequest
import com.example.myjournalapp.data.model.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GossipCentralAPI {
    @POST("auth/user")
    suspend fun register(@Body request : RegistrationRequest) : RegistrationResponse
}