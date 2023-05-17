package com.example.amernotsapp.data.network

import com.example.amernotsapp.data.model.request.SignInRequest
import com.example.amernotsapp.data.model.request.SignUpRequest
import com.example.amernotsapp.data.model.response.TokenAuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AmernotsApiService {

    @POST("auth/sign_up")
    suspend fun addNewUser(
        @Body signUpRequest: SignUpRequest
    ): TokenAuthResponse

    @POST("auth/sign_in")
    suspend fun checkSignIn(
        @Body signInRequest: SignInRequest
    ): TokenAuthResponse
}