package com.jjmin.mbliecontent.ui.blind

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.jjmin.mbliecontent.ClickActivity
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.util.TTSUtils

class BlindViewModel (val useCase: BlindUseCase) : ViewModel(){
    var tts = TTSUtils

    init {
        tts.speak("환영합니다." +
                " 찾고 싶으신 음식을 찾아드립니다. " +
                "좌측 상단 클릭은 한식이고," +
                "우측 상단 클릭은 중식이고," +
                "좌측 하단 클릭은 일식이고," +
                "우측 하단 클릭은 양식에 관한 설명을 드립니다," +
                "음식정보를 듣다가 원하는 음식이 나오시면 화면을 한 번 클릭하고 음성인식으로 찾아 주세요," +
                "뒤로가기는 길게 클릭 해주세요.")
    }

    var layout1Click = View.OnClickListener {
        var korea = getFoodName(useCase.korealist)
        if(korea == "") {
            tts.speak("한식은 존재하지 않습니다.")
        }else {
            tts.speak("한식은 ${korea}가 있습니다.")
            startvoice(useCase.korealist)

        }
    }

    var layout2Click = View.OnClickListener {
        var china = getFoodName(useCase.chinalist)
        if(china == ""){
            tts.speak("중식은 존재하지 않습니다.")
        }else {
            tts.speak("중식은 ${china}가 있습니다.")
            startvoice(useCase.chinalist)

        }
    }

    var layout3Click = View.OnClickListener {
        var japan = getFoodName(useCase.jspanlist)
        if(japan == "") {
            tts.speak("일식은 존재하지 않습니다.")
        }else{
            tts.speak("일식은 ${japan}가 있습니다.")
            startvoice(useCase.jspanlist)
        }

    }

    var layout4Click = View.OnClickListener {
        var america = getFoodName(useCase.americalist)
        if(america == ""){
            tts.speak("양식은 존재하지 않습니다.")
        }else {
            tts.speak("양식은 ${america}가 있습니다.")
            startvoice(useCase.americalist)
        }
    }


    fun getFoodName(list : ArrayList<SendShapeData>) : String{
        var info = ""
        (0 until list.size).forEach {
            info += "${list[it].name},"
        }

        return info
    }

    fun startvoice(list : ArrayList<SendShapeData>){
        var intent = Intent(useCase.activity,ClickActivity::class.java)
        intent.putExtra("list",list)
        useCase.activity.startActivity(intent)
    }

}