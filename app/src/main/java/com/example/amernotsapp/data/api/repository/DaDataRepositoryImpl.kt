package com.example.amernotsapp.data.api.repository

import com.example.amernotsapp.data.api.mappers.DaDataApiResponseMapper
import com.example.amernotsapp.data.api.model.request.DaDataCheckAddressRequest
import com.example.amernotsapp.data.api.network.DaDataService
import com.example.amernotsapp.domain.entity.DaDataSuggestionsEntity
import com.example.amernotsapp.domain.repository.DaDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DaDataRepositoryImpl @Inject constructor(
    private val remoteSource: DaDataService,
    private val daDataApiResponseMapper: DaDataApiResponseMapper,
) : DaDataRepository {
    override suspend fun checkAddress(
        apikey: String,
        daDataCheckAddressRequest: DaDataCheckAddressRequest,
    ): DaDataSuggestionsEntity {
        return withContext(Dispatchers.IO) {
            (daDataApiResponseMapper::mapCheckAddress)(
                remoteSource.checkAddress(
                    apiKey = apikey,
                    checkAddress = daDataCheckAddressRequest
                )
            )
        }
    }
}