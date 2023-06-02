package com.example.amernotsapp.ui.model.response

data class NewsByIdDataModel(
    val userStatus: String,
    val tittleSituation: String,
    val description: String,
    val address: String,
    val timeRelease: String,
    val urgencyCode: Int,
    val photo: String,
    val roleNews: String,
    val employeeId: Long,
)