package com.example.amernotsapp.domain.entity

import com.example.amernotsapp.ui.model.response.DaDataSuggestionsDataModel
import com.example.amernotsapp.ui.model.response.SuggestionDataModel

data class DaDataSuggestionsEntity(
    val suggestions: List<SuggestionDataModel>
)

fun DaDataSuggestionsEntity.mapDaDataSuggestionsEntity(): DaDataSuggestionsDataModel {
    return DaDataSuggestionsDataModel(
        suggestions = this.suggestions
    )
}
