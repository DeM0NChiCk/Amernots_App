package com.example.amernotsapp.data.api.repository

import com.example.amernotsapp.data.api.mappers.AmernotsApiResponseMapper
import com.example.amernotsapp.data.api.model.request.SignInRequest
import com.example.amernotsapp.data.api.model.request.SignUpRequest
import com.example.amernotsapp.data.api.network.AmernotsApiService
import com.example.amernotsapp.domain.entity.NewslineEntity
import com.example.amernotsapp.domain.entity.ProfileEntity
import com.example.amernotsapp.domain.entity.TokenAuthEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AmernotsApiReposotoryImpl @Inject constructor(
    private val remoteSource: AmernotsApiService,
    private val amernotsApiResponseMapper: AmernotsApiResponseMapper,
): AmernotsApiRepository {
    override suspend fun regNewUserRepository(signUpRequest: SignUpRequest): TokenAuthEntity {
        return withContext(Dispatchers.IO) {
            (amernotsApiResponseMapper::mapToken)(remoteSource.addNewUser(signUpRequest = signUpRequest))
        }
    }

    override suspend fun signInUserRepository(signInRequest: SignInRequest): TokenAuthEntity {
        return withContext(Dispatchers.IO) {
            (amernotsApiResponseMapper::mapToken)(remoteSource.checkSignIn(signInRequest = signInRequest))
        }
    }

    override suspend fun getProfileRepository(tokenAuthHeader: String): ProfileEntity {
        return withContext(Dispatchers.IO) {
            (amernotsApiResponseMapper::mapProfile)(remoteSource.getProfile(tokenAuthHeader = tokenAuthHeader))
        }
    }

    override suspend fun getNewslineRepository(tokenAuthHeader: String): NewslineEntity {
        return withContext(Dispatchers.IO) {
            (amernotsApiResponseMapper::mapNewsline)(remoteSource.getNewsline(tokenAuthHeader = tokenAuthHeader))
        }
    }
}