package com.jjmin.mbliecontent.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

@SuppressLint("StaticFieldLeak")
object SharedUtils {
    lateinit var FoodPreferences: SharedPreferences
    lateinit var getPreferences : SharedPreferences
    lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    fun foodShared(id: Int) {
        FoodPreferences = context.getSharedPreferences("Food$id", MODE_PRIVATE)
    }

    fun deletefood(size : Int){
        (0 until size).forEach {
            var a = context.getSharedPreferences("Food$size", MODE_PRIVATE)
            var editor =  a.edit()
            editor.clear()
        }
    }

    fun saveFood(foodName: String, foodCountry: String, foodExplan: String, foodAllergy: String, foodMeterial: String) {
        var editor = FoodPreferences.edit()
        editor.putString("foodName",foodName)
        editor.putString("foodCountry",foodCountry)
        editor.putString("foodExplan",foodExplan)
        editor.putString("foodAllergy",foodAllergy)
        editor.putString("foodMeterial",foodMeterial)
        editor.commit()
    }

    fun getShared(id : Int){
        getPreferences = context.getSharedPreferences("Food$id", MODE_PRIVATE)
    }

    fun getCountry(id: Int) : String{
        getShared(id)
        return getPreferences.getString("foodCountry","")
    }

    fun getxplan(id: Int) : String{
        getShared(id)
        return getPreferences.getString("foodExplan","")
    }

    fun getAllergy(id: Int) : String{
        getShared(id)
        return getPreferences.getString("foodAllergy","")
    }

    fun getMeterial(id: Int) : String{
        getShared(id)
        return getPreferences.getString("foodMeterial","")
    }

    fun getFood(id: Int) : String{
        getShared(id)
        return getPreferences.getString("foodName","")
    }
}