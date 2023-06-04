package com.example.amernotsapp.domain.repository

import com.example.amernotsapp.data.api.model.request.DaDataCheckAddressRequest
import com.example.amernotsapp.domain.entity.DaDataSuggestionsEntity

interface DaDataRepository {

    suspend fun checkAddress(apikey: String, daDataCheckAddressRequest: DaDataCheckAddressRequest): DaDataSuggestionsEntity
}