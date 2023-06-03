package com.example.amernotsapp.domain.usecase

import com.example.amernotsapp.domain.entity.mapChangeStatusMessageEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import com.example.amernotsapp.ui.model.response.ChangeStatusMessageDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChangeNewsStatusUseCase @Inject constructor(
    private val amernotsApiRepository: AmernotsApiRepository
){
    suspend operator fun invoke(
        tokenAuthHeader: String,
        news_id: String
    ): ChangeStatusMessageDataModel {
        return amernotsApiRepository.changeNewsStatus(tokenAuthHeader, news_id).mapChangeStatusMessageEntity()
    }
}