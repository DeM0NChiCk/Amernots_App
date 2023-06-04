package com.example.amernotsapp.data.api.model.response

import com.google.gson.annotations.SerializedName

data class DaDataSuggestionsResponse(
    @SerializedName("suggestions")
    val suggestions: List<Suggestion> ?= null
)

data class Suggestion(
    @SerializedName("value")
    val value: String,
    @SerializedName("unrestricted_value")
    val unrestrictedValue: String
)