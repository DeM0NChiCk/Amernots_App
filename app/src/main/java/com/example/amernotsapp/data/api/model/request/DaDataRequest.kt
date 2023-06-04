package com.example.amernotsapp.data.api.model.request

import com.google.gson.annotations.SerializedName

data class DaDataCheckAddressRequest(
    @SerializedName("query")
    val query: String? = null
)