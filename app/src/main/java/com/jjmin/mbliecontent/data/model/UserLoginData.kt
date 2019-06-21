package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName

data class UserLoginData(
    @SerializedName("success")
    var success: Boolean? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("food")
    var location : ArrayList<ArrangementData>? = null
)