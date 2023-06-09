package com.example.amernotsapp.data.api.mappers

import com.example.amernotsapp.data.api.model.response.NewsByIdResponse
import com.example.amernotsapp.data.api.model.response.NewslineResponse
import com.example.amernotsapp.data.api.model.response.ChangeStatusMessage
import com.example.amernotsapp.data.api.model.response.ProfileResponse
import com.example.amernotsapp.data.api.model.response.TokenAuthResponse
import com.example.amernotsapp.domain.entity.NewsByIdEntity
import com.example.amernotsapp.domain.entity.NewslineEntity
import com.example.amernotsapp.domain.entity.ChangeStatusMessageEntity
import com.example.amernotsapp.domain.entity.ProfileEntity
import com.example.amernotsapp.domain.entity.TokenAuthEntity
import com.example.amernotsapp.ui.model.response.NewsDataModel
import javax.inject.Inject

class AmernotsApiResponseMapper @Inject constructor() {

    fun mapToken(item: TokenAuthResponse?): TokenAuthEntity {
        return item?.let { response ->
            with(response) {
                TokenAuthEntity(
                    token = token ?: ""
                )
            }
        } ?: TokenAuthEntity(
            token = ""
        )
    }

    fun mapProfile(item: ProfileResponse?): ProfileEntity {
        return item?.let { response ->
            with(response) {
                ProfileEntity(
                    username = username ?: "",
                    login = login ?: "",
                    id = id ?: 0L,
                    userStatus = userStatus ?: "",
                    newslineUser = newslineUser?.map { news ->
                        NewsDataModel(
                            newslineId = news.newslineId,
                            tittleSituation = news.tittleSituation,
                            description = news.description,
                            address = news.address,
                            timeRelease = news.timeRelease,
                            urgencyCode = news.urgencyCode,
                            roleNews = news.roleNews,
                            authorId = news.authorId,
                            employeeId = news.employeeId
                        )
                    } ?: listOf(
                        NewsDataModel(
                            newslineId = 0L,
                            tittleSituation = "",
                            description = "",
                            address = "",
                            timeRelease = "",
                            urgencyCode = 0,
                            roleNews = "",
                            authorId = 0L,
                            employeeId = 0L
                        )
                    )
                )
            }
        } ?: ProfileEntity(
            userStatus = "",
            login = "",
            id = 0L,
            username = "",
            newslineUser = listOf(
                NewsDataModel(
                    newslineId = 0L,
                    tittleSituation = "",
                    description = "",
                    address = "",
                    timeRelease = "",
                    urgencyCode = 0,
                    roleNews = "",
                    authorId = 0L,
                    employeeId = 0L
                )
            )
        )
    }

    fun mapNewsline(item: NewslineResponse?): NewslineEntity {
        return item?.let { response ->
            with(response) {
                NewslineEntity(
                    userStatus = userStatus ?: "",
                    newsline = newsline?.map { news ->
                        NewsDataModel(
                            newslineId = news.newslineId,
                            tittleSituation = news.tittleSituation,
                            description = news.description,
                            address = news.address,
                            timeRelease = news.timeRelease,
                            urgencyCode = news.urgencyCode,
                            roleNews = news.roleNews,
                            authorId = news.authorId,
                            employeeId = news.employeeId
                        )
                    } ?: listOf(
                        NewsDataModel(
                            newslineId = 0L,
                            tittleSituation = "",
                            description = "",
                            address = "",
                            timeRelease = "",
                            urgencyCode = 0,
                            roleNews = "",
                            authorId = 0L,
                            employeeId = 0L
                        )
                    ),
                )
            }

        } ?: NewslineEntity(
            userStatus = "",
            newsline = listOf(
                NewsDataModel(
                    newslineId = 0L,
                    tittleSituation = "",
                    description = "",
                    address = "",
                    timeRelease = "",
                    urgencyCode = 0,
                    roleNews = "",
                    authorId = 0L,
                    employeeId = 0L
                )
            )
        )
    }

    fun mapNewsById(item: NewsByIdResponse?): NewsByIdEntity {
        return item?.let { response ->
            with(response) {
                NewsByIdEntity(
                    userStatus = userStatus ?: "",
                    tittleSituation = tittleSituation ?: "",
                    description = description ?: "",
                    address = address ?: "",
                    timeRelease = timeRelease ?: "",
                    urgencyCode = urgencyCode ?: 0,
                    roleNews = roleNews ?: "",
                    employeeId = employeeId ?: 0L,
                )
            }
        } ?: NewsByIdEntity(
            userStatus = "",
            tittleSituation = "",
            description = "",
            address = "",
            timeRelease = "",
            urgencyCode = 0,
            roleNews = "",
            employeeId = 0L
        )
    }

    fun mapChangeStatusMessage(item: ChangeStatusMessage?): ChangeStatusMessageEntity {
        return item?.let { response ->
            with(response) {
                ChangeStatusMessageEntity(
                    message = message ?: ""
                )
            }
        } ?: ChangeStatusMessageEntity(
            message = ""
        )
    }


}