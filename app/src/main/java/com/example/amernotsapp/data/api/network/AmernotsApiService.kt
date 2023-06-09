package com.example.amernotsapp.data.api.network

import com.example.amernotsapp.data.api.model.request.AddNewsRequest
import com.example.amernotsapp.data.api.model.request.ChangePasswordRequest
import com.example.amernotsapp.data.api.model.request.SignInRequest
import com.example.amernotsapp.data.api.model.request.SignUpRequest
import com.example.amernotsapp.data.api.model.response.NewsByIdResponse
import com.example.amernotsapp.data.api.model.response.NewslineResponse
import com.example.amernotsapp.data.api.model.response.ChangeStatusMessage
import com.example.amernotsapp.data.api.model.response.ProfileResponse
import com.example.amernotsapp.data.api.model.response.TokenAuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("news")
    suspend fun getNewsById(
        @Header("Authorization") tokenAuthHeader: String,
        @Query("news_id") news_id: String
    ): NewsByIdResponse

    @PATCH("change/password")
    suspend fun ChangePassword(
        @Header("Authorization") tokenAuthHeader: String,
        @Body changePasswordRequest: ChangePasswordRequest,
    ): ChangeStatusMessage

    @PATCH("change/news_status")
    suspend fun ChangeNewsStatus(
        @Header("Authorization") tokenAuthHeader: String,
        @Query("news_id") news_id: String
    ): ChangeStatusMessage

    @POST("add/news")
    suspend fun AddNews(
        @Header("Authorization") tokenAuthHeader: String,
        @Body addNewsRequest: AddNewsRequest
    ): ChangeStatusMessage
}