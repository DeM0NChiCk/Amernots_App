package com.example.amernotsapp.data.model.request

import com.google.gson.annotations.SerializedName

data class RegRequest(
    @SerializedName("login")
    val login: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("userStatus")
    val userStatus: Int?,
)
