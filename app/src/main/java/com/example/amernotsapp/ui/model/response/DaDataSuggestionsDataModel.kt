package com.example.amernotsapp.ui.model.response

data class DaDataSuggestionsDataModel(
    val suggestions: List<SuggestionDataModel>
)

data class SuggestionDataModel(
    val value: String,
    val unrestrictedValue: String
)
