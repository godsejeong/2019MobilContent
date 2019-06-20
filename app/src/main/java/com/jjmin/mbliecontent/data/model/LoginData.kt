package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName

class LoginData{
    @SerializedName("success")
    val success: Boolean? = null

    @SerializedName("message")
    val message: String? = null

    @SerializedName("user")
    val user : UserInfo? = null
}