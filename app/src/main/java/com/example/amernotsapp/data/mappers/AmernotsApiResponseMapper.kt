package com.example.amernotsapp.data.mappers

import com.example.amernotsapp.data.model.response.TokenAuthResponse
import com.example.amernotsapp.domain.entity.TokenAuthEntity
import javax.inject.Inject

class AmernotsApiResponseMapper @Inject constructor() {

    fun mapToken(item: TokenAuthResponse?): TokenAuthEntity {
        return item?.let { response ->
            with(response) {
                TokenAuthEntity(
                    token = token ?:""
                )
            }
        } ?: TokenAuthEntity(
            token = ""
        )
    }
}