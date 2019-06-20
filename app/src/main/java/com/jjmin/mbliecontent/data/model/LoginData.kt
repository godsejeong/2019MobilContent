package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("success")
    val success: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("user")
    val user : UserInfo
    )