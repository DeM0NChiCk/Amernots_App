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
