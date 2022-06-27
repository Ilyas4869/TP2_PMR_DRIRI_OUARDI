package com.example.myapplication.data.api


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface ItemsService {

    @GET("lists/{listId}/items")
    suspend fun getItems(@Path("listId") listId: Long, @Header("hash") hash: String) : ItemsResponse
}
