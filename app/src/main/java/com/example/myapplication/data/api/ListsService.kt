package com.example.myapplication.data.api


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface ListsService {

    @GET("lists")
    suspend fun getLists(@Header("hash") hash: String) : ListsResponse
}
