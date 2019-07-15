package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class UserInfo (
    @PrimaryKey
    @SerializedName("id")
    var id : String = "",

    @SerializedName("passwd")
    var passwd : String = "",

    @SerializedName("name")
    var companyName : String = "",

    @SerializedName("email")
    var companyEmail : String = "",

    @SerializedName("pn")
    var phoneNumber : String = "",

    @SerializedName("_id")
    var token : String = "",

    @SerializedName("is_admin")
    var is_admin : Boolean = false,

    @SerializedName("__v")
    var __v : Int? = null
) : RealmObject()