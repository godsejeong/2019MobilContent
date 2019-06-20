package com.jjmin.mbliecontent.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

open class UserInfo (
    @io.realm.annotations.PrimaryKey
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

    @SerializedName("is_admin")
    var is_admin : Boolean = false,

    @SerializedName("__v")
    var __v : Int? = null
) : RealmObject()