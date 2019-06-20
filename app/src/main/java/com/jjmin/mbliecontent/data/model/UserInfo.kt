package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName

data class UserInfo (
    @SerializedName("id")
    val id : String,

    @SerializedName("passwd")
    val passwd : String,

    @SerializedName("name")
    val companyName : String,

    @SerializedName("email")
    val companyEmail : String,

    @SerializedName("pn")
    val phoneNumber : String,

    @SerializedName("is_admin")
    val is_admin : String,

    @SerializedName("__v")
    val __v : String
)