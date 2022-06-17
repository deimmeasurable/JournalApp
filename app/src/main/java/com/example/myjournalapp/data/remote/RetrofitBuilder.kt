package com.example.myjournalapp.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    val baseUrl ="http://gossip-central-dev.herokuapp.com/api/v1/"
    fun getInstance(): Retrofit{
        return Retrofit .Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val gossipCentralAPI: GossipCentralAPI = getInstance()
        .create(GossipCentralAPI::class.java)
}