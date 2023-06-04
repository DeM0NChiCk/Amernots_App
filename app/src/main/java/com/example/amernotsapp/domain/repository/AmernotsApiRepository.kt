package com.example.amernotsapp.domain.repository

import com.example.amernotsapp.data.api.model.request.AddNewsRequest
import com.example.amernotsapp.data.api.model.request.ChangePasswordRequest
import com.example.amernotsapp.data.api.model.request.SignInRequest
import com.example.amernotsapp.data.api.model.request.SignUpRequest
import com.example.amernotsapp.domain.entity.NewsByIdEntity
import com.example.amernotsapp.domain.entity.NewslineEntity
import com.example.amernotsapp.domain.entity.ChangeStatusMessageEntity
import com.example.amernotsapp.domain.entity.ProfileEntity
import com.example.amernotsapp.domain.entity.TokenAuthEntity

interface AmernotsApiRepository {
    suspend fun regNewUserRepository(signUpRequest: SignUpRequest): TokenAuthEntity

    suspend fun signInUserRepository(signInRequest: SignInRequest): TokenAuthEntity

    suspend fun getProfileRepository(tokenAuthHeader: String): ProfileEntity

    suspend fun getNewslineRepository(tokenAuthHeader: String): NewslineEntity

    suspend fun getNewsByIdRepository(tokenAuthHeader: String, newsId: String): NewsByIdEntity

    suspend fun changePassword(tokenAuthHeader: String, changePasswordRequest: ChangePasswordRequest): ChangeStatusMessageEntity

    suspend fun changeNewsStatus(tokenAuthHeader: String, news_id: String): ChangeStatusMessageEntity

    suspend fun addNews(tokenAuthHeader: String, addNewsRequest: AddNewsRequest): ChangeStatusMessageEntity
}