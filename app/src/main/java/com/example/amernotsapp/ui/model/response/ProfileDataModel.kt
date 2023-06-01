package com.example.amernotsapp.ui.model.response

data class ProfileDataModel(
    val username: String,
    val login: String,
    val id: Long,
    val userStatus: String,
    val newslineUser: List<NewsDataModel>,
)

data class NewsDataModel(
    val newslineId: Long,
    val tittleSituation: String,
    val description: String,
    val address: String,
    val timeRelease: String,
    val urgencyCode: Int,
    val photo: String,
    val roleNews: String,
    val authorId: Long,
    val employeeId: Long
)