package com.example.amernotsapp.data.network

import com.example.amernotsapp.data.model.request.RegRequest
import com.example.amernotsapp.data.model.response.TokenAuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AmernotsApiService {

    @POST("auth/sing_in")
    suspend fun addNewUser(
        @Body regRequest: RegRequest
    ): TokenAuthResponse
}