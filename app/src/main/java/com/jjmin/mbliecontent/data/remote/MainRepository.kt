package com.jjmin.mbliecontent.data.remote

import com.google.gson.JsonArray
import com.jjmin.mbliecontent.data.model.BaseData
import com.jjmin.mbliecontent.data.model.SendShapeData
import io.reactivex.Single
import org.json.JSONArray

interface MainRepository{
    fun SendShape(array : String, id : String) : Single<BaseData>
}