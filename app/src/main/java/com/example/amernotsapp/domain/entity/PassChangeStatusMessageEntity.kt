package com.example.amernotsapp.domain.entity

import com.example.amernotsapp.ui.model.response.PassChangeStatusMessageDataModel

data class PassChangeStatusMessageEntity(
    val message: String
)

fun PassChangeStatusMessageEntity.mapPassChangeStatusMessageEntity(): PassChangeStatusMessageDataModel {
    return PassChangeStatusMessageDataModel(
        message = message
    )
}
