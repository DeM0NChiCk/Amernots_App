package com.example.amernotsapp.domain.repository

import com.example.amernotsapp.data.model.request.SignInRequest
import com.example.amernotsapp.data.model.request.SignUpRequest
import com.example.amernotsapp.domain.entity.TokenAuthEntity

interface AmernotsApiRepository {
    suspend fun regNewUserRepository(signUpRequest: SignUpRequest): TokenAuthEntity

    suspend fun signInUserRepository(signInRequest: SignInRequest): TokenAuthEntity
}