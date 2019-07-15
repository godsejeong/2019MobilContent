package com.jjmin.mbliecontent.data.remote

import com.google.gson.JsonArray
import com.jjmin.mbliecontent.data.model.BaseData
import com.jjmin.mbliecontent.data.model.SendShapeData
import io.reactivex.Single
import org.json.JSONArray

class MainRepositoryImpl(var api: NetworkApi) : MainRepository{
    override fun SendShape(array: String, id : String): Single<BaseData> {
        return api.SendShape(array,id).map { it }
    }
}