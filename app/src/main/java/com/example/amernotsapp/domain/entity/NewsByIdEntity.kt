package com.example.amernotsapp.domain.entity

import com.example.amernotsapp.ui.model.response.NewsByIdDataModel

data class NewsByIdEntity(
    val tittleSituation: String,
    val description: String,
    val address: String,
    val timeRelease: String,
    val urgencyCode: Int,
    val photo: String,
    val roleNews: String,
    val employeeId: Long
)

fun NewsByIdEntity.mapNewsByIdEntity(): NewsByIdDataModel {
    return NewsByIdDataModel(
        tittleSituation = this.tittleSituation,
        description = this.description,
        address = this.address,
        timeRelease = this.timeRelease,
        urgencyCode = this.urgencyCode,
        photo = this.photo,
        roleNews = this.roleNews,
        employeeId = this.employeeId
    )
}