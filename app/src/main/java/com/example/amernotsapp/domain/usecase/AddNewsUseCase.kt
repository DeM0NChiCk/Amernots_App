package com.example.amernotsapp.domain.usecase

import com.example.amernotsapp.data.api.model.request.AddNewsRequest
import com.example.amernotsapp.domain.entity.mapChangeStatusMessageEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import com.example.amernotsapp.ui.model.response.ChangeStatusMessageDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddNewsUseCase @Inject constructor(
    private val amernotsApiRepository: AmernotsApiRepository
) {
    suspend operator fun invoke(
        tokenAuthHeader: String,
        addNewsRequest: AddNewsRequest
    ): ChangeStatusMessageDataModel {
        return amernotsApiRepository.addNews(tokenAuthHeader, addNewsRequest).mapChangeStatusMessageEntity()
    }
}