package com.example.amernotsapp.data.api.network

import com.example.amernotsapp.data.api.model.request.SignInRequest
import com.example.amernotsapp.data.api.model.request.SignUpRequest
import com.example.amernotsapp.data.api.model.response.NewslineResponse
import com.example.amernotsapp.data.api.model.response.ProfileResponse
import com.example.amernotsapp.data.api.model.response.TokenAuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AmernotsApiService {

    @POST("auth/sign_up")
    suspend fun addNewUser(
        @Body signUpRequest: SignUpRequest
    ): TokenAuthResponse

    @POST("auth/sign_in")
    suspend fun checkSignIn(
        @Body signInRequest: SignInRequest,
    ): TokenAuthResponse

    @GET("profile")
    suspend fun getProfile(
        @Header("Authorization") tokenAuthHeader: String
    ): ProfileResponse

    @GET("newsline")
    suspend fun getNewsline(
        @Header("Authorization") tokenAuthHeader: String
    ): NewslineResponse
}