package com.example.amernotsapp.data.api.network

import com.example.amernotsapp.data.api.model.request.DaDataCheckAddressRequest
import com.example.amernotsapp.data.api.model.response.DaDataSuggestionsResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface DaDataService {
    @POST("address")
    suspend fun checkAddress(
        @Header("Authorization") apiKey: String,
        @Body checkAddress: DaDataCheckAddressRequest
    ): DaDataSuggestionsResponse
}