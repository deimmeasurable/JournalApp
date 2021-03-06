package com.example.myjournalapp.data.remote

import com.example.myjournalapp.data.model.LogInRequest
import com.example.myjournalapp.data.model.LogInResponse
import com.example.myjournalapp.data.model.RegistrationRequest
import com.example.myjournalapp.data.model.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GossipCentralAPI {
    @POST("auth/user")
    suspend fun register(@Body request : RegistrationRequest) : RegistrationResponse

    @POST("auth/authenticate")
    suspend fun logIn(@Body request : LogInRequest): LogInResponse
}