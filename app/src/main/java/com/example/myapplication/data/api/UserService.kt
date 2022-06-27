package com.example.myapplication.data.api

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @POST("authenticate")
    suspend fun getUserHash(@Body info: RequestBody) : AuthenticationResponse
}