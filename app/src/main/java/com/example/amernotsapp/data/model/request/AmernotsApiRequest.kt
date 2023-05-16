package com.example.amernotsapp.data.model.request

import com.google.gson.annotations.SerializedName

data class RegRequest(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("userStatus")
    val userStatus: Int? = null,
)
