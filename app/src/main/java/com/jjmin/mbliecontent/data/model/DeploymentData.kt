package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class DeploymentData(
    @SerializedName("success")
    var success: Boolean? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("locates")
    var locates : String? = null
)