package com.example.amernotsapp.domain.repository

import com.example.amernotsapp.data.model.request.RegRequest
import com.example.amernotsapp.domain.entity.TokenAuthEntity

interface AmernotsApiRepository {

    suspend fun regNewUserRepository(regRequest: RegRequest): TokenAuthEntity


}