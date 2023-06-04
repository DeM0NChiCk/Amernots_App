package com.example.amernotsapp.data.api.model.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("userStatus")
    val userStatus: String? = null,
)

data class SignInRequest(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("password")
    val password: String? = null,
)

data class ChangePasswordRequest(
    @SerializedName("oldPassword")
    val oldPassword: String? = null,
    @SerializedName("newPassword")
    val newPassword: String? = null
)

data class AddNewsRequest(
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
    @SerializedName("roleNews")
    val roleNews: String,
)
