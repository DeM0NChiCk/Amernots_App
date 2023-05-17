package com.example.amernotsapp.data.model.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("userStatus")
    val userStatus: Int? = null,
)

data class SignInRequest(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("password")
    val password: String? = null,
)
