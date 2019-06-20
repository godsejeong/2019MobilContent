package com.jjmin.mbliecontent.util

import android.content.Context
import ninja.sakib.pultusorm.core.PultusORM

object ORMUtils{
    lateinit var appPath : String

    fun setORM(context: Context){
        appPath = context.filesDir.absolutePath
    }

    fun UserDB() : PultusORM{
        return PultusORM("login.db", appPath)

    }
}