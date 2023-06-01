package com.example.amernotsapp.domain.usecase

import com.example.amernotsapp.domain.entity.mapNewslineEntity
import com.example.amernotsapp.domain.repository.AmernotsApiRepository
import com.example.amernotsapp.ui.model.response.NewslineDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetNeswlineUseCase @Inject constructor(
    private val amernotsApiRepository: AmernotsApiRepository
){
    suspend operator fun invoke(tokenAuthHeader: String): NewslineDataModel {
        return amernotsApiRepository.getNewslineRepository(tokenAuthHeader).mapNewslineEntity()
    }
}