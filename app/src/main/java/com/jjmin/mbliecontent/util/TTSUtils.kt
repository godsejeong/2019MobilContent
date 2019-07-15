package com.jjmin.mbliecontent.util

import android.content.Context
import android.os.Build
import android.widget.Toast
import com.jjmin.mbliecontent.ui.main.MainActivity
import android.speech.tts.TextToSpeech
import java.util.*


object TTSUtils{
    lateinit var textToSpeech : TextToSpeech

    fun init(context: Context){
         textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                //사용할 언어를 설정
                val result = textToSpeech.setLanguage(Locale.KOREA)
                //언어 데이터가 없거나 혹은 언어가 지원하지 않으면...
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(context, "이 언어는 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    //음성 톤
                    textToSpeech.setPitch(1f)
                    //읽는 속도
                    textToSpeech.setSpeechRate(1.2f)
                }
            }
        })
    }

    fun speak(text : String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        // API 20
        else
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun stop(){
        if (textToSpeech != null) {
            textToSpeech.stop()
        }
    }
}