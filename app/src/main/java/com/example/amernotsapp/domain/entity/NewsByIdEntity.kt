package com.example.amernotsapp.domain.entity

import com.example.amernotsapp.ui.model.response.NewsByIdDataModel

data class NewsByIdEntity(
    val userStatus: String,
    val tittleSituation: String,
    val description: String,
    val address: String,
    val timeRelease: String,
    val urgencyCode: Int,
    val roleNews: String,
    val employeeId: Long
)

fun NewsByIdEntity.mapNewsByIdEntity(): NewsByIdDataModel {
    return NewsByIdDataModel(
        userStatus = userStatus,
        tittleSituation = this.tittleSituation,
        description = this.description,
        address = this.address,
        timeRelease = this.timeRelease,
        urgencyCode = this.urgencyCode,
        roleNews = this.roleNews,
        employeeId = this.employeeId
    )
}