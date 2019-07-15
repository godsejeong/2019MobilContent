package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName

data class Location (
  @SerializedName("location")
  var location : ArrayList<String >? = null,
  @SerializedName("id")
  var id : String = ""
)