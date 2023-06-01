package com.example.amernotsapp.domain.usecase

import com.example.amernotsapp.data.api.model.request.SignInRequest
import com.example.amernotsapp.domain.entity.mapTokenAuthEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import com.example.amernotsapp.ui.model.response.TokenAuthDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInUseCase @Inject constructor(
    private val amernotsApiRepository: AmernotsApiRepository,
){
    suspend operator fun invoke(signInRequest: SignInRequest): TokenAuthDataModel{
        return amernotsApiRepository.signInUserRepository(signInRequest).mapTokenAuthEntity()
    }
}