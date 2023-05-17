package com.example.amernotsapp.domain.usecase

import com.example.amernotsapp.data.model.request.SignUpRequest
import com.example.amernotsapp.domain.entity.mapTokenAuthEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import com.example.amernotsapp.ui.model.response.TokenAuthDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpNewUserUseCase @Inject constructor(
    private val amernotsApiRepository: AmernotsApiRepository
) {
    suspend operator fun invoke(signUpRequest: SignUpRequest): TokenAuthDataModel {
        return amernotsApiRepository.regNewUserRepository(signUpRequest).mapTokenAuthEntity()
    }
}