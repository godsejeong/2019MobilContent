package com.jjmin.mbliecontent.data.model

import java.io.Serializable

data class SendShapeData(
    var x : Float = 0f,
    var y : Float = 0f,
    var id : Int = 0,
    var num : Int = 0,
    var color : Int = 0,
    var country : String = "",
    var explain : String = "",
    var material : String = "",
    var allergy : String = "",
    var name : String = ""
) : Serializable