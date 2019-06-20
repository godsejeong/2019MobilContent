package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName

data class BaseData(
    @SerializedName("success")
    var success: Boolean? = null,

    @SerializedName("message")
    var message: String? = null
)