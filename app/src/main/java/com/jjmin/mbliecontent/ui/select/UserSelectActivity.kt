package com.jjmin.mbliecontent.ui.select

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.util.TTSUtils
import kotlinx.android.synthetic.main.activity_user_select.*

class UserSelectActivity : Activity() {
    var tts = TTSUtils
    var allergy = ""
    var material = ""
    var explain = ""
    var country = ""
    var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        )
        setContentView(R.layout.activity_user_select)

        name = intent.getStringExtra("name")
        allergy = intent.getStringExtra("allergy")
        material = intent.getStringExtra("material")
        explain = intent.getStringExtra("explain")
        country = intent.getStringExtra("country")

        tts.speak(
            "이 음식은 $name 입니다." +

                    "좌측 상단 클릭은 음식 설명이고" +

                    "우측 상단 클릭은 주 재료이고" +
                    "    " +
                    "좌측 하단 클릭은 알레르기이고" +
                    "   " +
                    "우측 하단 클릭은은 뒤로가기 입니다."
        )

        //눈깔 보임
        //좌측 상단 - 음식 설명
        //우측 상단 - 주 재료
        //좌측 하단 - 알래르기
        //우측 하단 - 취소 - 뒤로가기

        //좌측 상단
        layout1.setOnClickListener {
            tts.speak("$material")
        }

        //우측 상단
        layout2.setOnClickListener {
            tts.speak("주 재료는 $material 입니다.")
        }

        //좌측 하단
        layout3.setOnClickListener {
            tts.speak("알레르기 유발 재료는 $allergy 입니다.")
        }

        //우측 하단
        layout4.setOnClickListener {
            tts.speak("뒤로갑니다.")
            finish()
            overridePendingTransition(0, 0)
        }
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
//            tts.stop()
            overridePendingTransition(0, 0)
        }
    }
}
