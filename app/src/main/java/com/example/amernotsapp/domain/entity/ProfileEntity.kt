package com.example.amernotsapp.domain.entity

import com.example.amernotsapp.ui.model.response.NewsDataModel
import com.example.amernotsapp.ui.model.response.ProfileDataModel

data class ProfileEntity(
    val username: String,
    val login: String,
    val id: Long,
    val userStatus: String,
    val newslineUser: List<NewsDataModel>,
)

fun ProfileEntity.mapProfileEntity(): ProfileDataModel {
    return ProfileDataModel(
        username = this.username,
        login = this.login,
        id = this.id,
        userStatus = this.userStatus,
        newslineUser = this.newslineUser
    )
}