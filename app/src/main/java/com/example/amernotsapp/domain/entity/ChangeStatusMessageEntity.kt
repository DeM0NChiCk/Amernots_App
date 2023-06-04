package com.example.amernotsapp.domain.entity

import com.example.amernotsapp.ui.model.response.ChangeStatusMessageDataModel

data class ChangeStatusMessageEntity(
    val message: String
)

fun ChangeStatusMessageEntity.mapChangeStatusMessageEntity(): ChangeStatusMessageDataModel {
    return ChangeStatusMessageDataModel(
        message = this.message
    )
}
