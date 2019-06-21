package com.jjmin.mbliecontent.util

import android.content.Context
import android.service.autofill.UserData
import android.util.Log
import com.jjmin.mbliecontent.data.model.UserInfo
import io.realm.Realm

object RealmUtils{
    var realm = Realm.getDefaultInstance()
    var userdata = realm.where(UserInfo::class.java).findAll()

    fun getCompanyName() : String{
        var companyName = ""
        userdata.forEach{
            companyName = it.companyName
        }
       return companyName
    }

    fun getCompanyId() : String{
        var companyId = ""
        userdata.forEach{
            companyId = it.id
        }
        return companyId
    }

}