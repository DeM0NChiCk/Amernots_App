package com.example.amernotsapp.domain.usecase

import com.example.amernotsapp.domain.entity.mapNewsByIdEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import com.example.amernotsapp.ui.model.response.NewsByIdDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNewsByIdUseCase @Inject constructor(
    private val amernotsApiRepository: AmernotsApiRepository
){
    suspend operator fun invoke(tokenAuthHeader: String, newsId: String): NewsByIdDataModel {
        return amernotsApiRepository.getNewsByIdRepository(tokenAuthHeader, newsId).mapNewsByIdEntity()
    }
}