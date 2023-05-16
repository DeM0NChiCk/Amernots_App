package com.example.amernotsapp.domain.entity

import com.example.amernotsapp.ui.model.response.TokenAuthDataModel

data class TokenAuthEntity(
    val token: String,
)

fun TokenAuthEntity.mapTokenAuthEntity(): TokenAuthDataModel {
    return TokenAuthDataModel(
        token = this.token
    )
}