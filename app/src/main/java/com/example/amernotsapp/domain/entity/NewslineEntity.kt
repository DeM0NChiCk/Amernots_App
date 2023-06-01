package com.example.amernotsapp.domain.entity

import com.example.amernotsapp.ui.model.response.NewsDataModel
import com.example.amernotsapp.ui.model.response.NewslineDataModel

data class NewslineEntity (
    val newsline: List<NewsDataModel>,
    val userStatus: String
)

fun NewslineEntity.mapNewslineEntity(): NewslineDataModel {
    return NewslineDataModel(
        newsline = this.newsline,
        userStatus = this.userStatus
    )
}