package com.jjmin.mbliecontent.data.model

import android.graphics.Paint

data class ArrangementData(
    var id: String,
    var num: String,
    var name : String,
    var email : String,
    var pn : String,
    var location : List<SendShapeData>
)