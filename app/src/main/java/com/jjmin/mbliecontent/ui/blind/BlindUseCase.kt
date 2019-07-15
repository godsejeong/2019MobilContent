package com.jjmin.mbliecontent.ui.blind

import com.jjmin.mbliecontent.data.model.SendShapeData

class BlindUseCase(val activity: BlindUserActivity){
    var bundle = activity.intent.extras

    var korealist = bundle.getSerializable("korealist") as ArrayList<SendShapeData>
    var jspanlist = bundle.getSerializable("jspanlist") as ArrayList<SendShapeData>
    var chinalist = bundle.getSerializable("chinalist") as ArrayList<SendShapeData>
    var americalist = bundle.getSerializable("americalist") as ArrayList<SendShapeData>
}