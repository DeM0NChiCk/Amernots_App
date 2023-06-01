package com.example.amernotsapp.domain.usecase

import com.example.amernotsapp.domain.entity.mapProfileEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import com.example.amernotsapp.ui.model.response.ProfileDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetProfileUseCase @Inject constructor(
    private val amernotsApiRepository: AmernotsApiRepository
){
    suspend operator fun invoke(tokenAuthHeader: String): ProfileDataModel {
        return amernotsApiRepository.getProfileRepository(tokenAuthHeader).mapProfileEntity()
    }
}