package com.example.amernotsapp.data.model.response

import com.google.gson.annotations.SerializedName

data class TokenAuthResponse(
    @SerializedName("token")
    val token: String? = null,
)
