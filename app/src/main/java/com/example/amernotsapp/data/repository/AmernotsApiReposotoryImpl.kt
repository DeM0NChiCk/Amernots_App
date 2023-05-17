package com.example.amernotsapp.data.repository

import com.example.amernotsapp.data.mappers.AmernotsApiResponseMapper
import com.example.amernotsapp.data.model.request.SignInRequest
import com.example.amernotsapp.data.model.request.SignUpRequest
import com.example.amernotsapp.data.network.AmernotsApiService
import com.example.amernotsapp.domain.entity.TokenAuthEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AmernotsApiReposotoryImpl @Inject constructor(
    private val remoteSource: AmernotsApiService,
    private val amernotsApiResponseMapper: AmernotsApiResponseMapper
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


}