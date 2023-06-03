package com.example.amernotsapp.data.api.model.response

import com.google.gson.annotations.SerializedName

data class TokenAuthResponse(
    @SerializedName("token")
    val token: String? = null,
)

data class ProfileResponse(
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("userStatus")
    val userStatus: String? = null,
    @SerializedName("newslineUser")
    val newslineUser: List<News> ?= null
)

data class News(
    @SerializedName("newslineId")
    val newslineId: Long,
    @SerializedName("tittleSituation")
    val tittleSituation: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("timeRelease")
    val timeRelease: String,
    @SerializedName("urgencyCode")
    val urgencyCode: Int,
    @SerializedName("photo")
    val photo: String,
    @SerializedName("roleNews")
    val roleNews: String,
    @SerializedName("authorId")
    val authorId: Long,
    @SerializedName("employeeId")
    val employeeId: Long
)

data class NewslineResponse(
    @SerializedName("newsline")
    val newsline: List<News> ?= null,
    @SerializedName("userStatus")
    val userStatus: String? = null
)

data class NewsByIdResponse(
    @SerializedName("userStatus")
    val userStatus: String? = null,
    @SerializedName("tittleSituation")
    val tittleSituation: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("address")
    val address: String? = null,
    @SerializedName("timeRelease")
    val timeRelease: String? = null,
    @SerializedName("urgencyCode")
    val urgencyCode: Int? = null,
    @SerializedName("photo")
    val photo: String? = null,
    @SerializedName("roleNews")
    val roleNews: String? = null,
    @SerializedName("employeeId")
    val employeeId: Long? = null
)

data class ChangeStatusMessage(
    @SerializedName("message")
    val message: String? = null
)
