package com.example.amernotsapp.domain.usecase

import com.example.amernotsapp.data.api.model.request.DaDataCheckAddressRequest
import com.example.amernotsapp.domain.entity.mapDaDataSuggestionsEntity
import com.example.amernotsapp.domain.repository.DaDataRepository
import com.example.amernotsapp.ui.model.response.DaDataSuggestionsDataModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckAddressUseCase @Inject constructor(
    private val daDataRepository: DaDataRepository
){
    suspend operator fun invoke(
        apiKey: String,
        daDataCheckAddressRequest: DaDataCheckAddressRequest
    ): DaDataSuggestionsDataModel {
        return daDataRepository.checkAddress(apiKey, daDataCheckAddressRequest).mapDaDataSuggestionsEntity()
    }
}