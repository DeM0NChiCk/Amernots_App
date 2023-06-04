package com.example.amernotsapp.data.api.mappers

import com.example.amernotsapp.data.api.model.response.DaDataSuggestionsResponse
import com.example.amernotsapp.domain.entity.DaDataSuggestionsEntity
import com.example.amernotsapp.ui.model.response.SuggestionDataModel
import javax.inject.Inject

class DaDataApiResponseMapper @Inject constructor(){
    fun mapCheckAddress(item: DaDataSuggestionsResponse?): DaDataSuggestionsEntity {
        return item?.let { response ->
            with(response) {
                DaDataSuggestionsEntity(
                    suggestions = suggestions?.map {suggestion ->
                        SuggestionDataModel(
                            value = suggestion.value ?: "",
                            unrestrictedValue = suggestion.unrestrictedValue ?: ""
                        )
                    } ?: listOf(
                        SuggestionDataModel(
                            value = "",
                            unrestrictedValue = ""
                        )
                    )
                )
            }

        } ?: DaDataSuggestionsEntity(
            suggestions = listOf(
                SuggestionDataModel(
                    value = "",
                    unrestrictedValue = ""
                )
            )
        )
    }
}