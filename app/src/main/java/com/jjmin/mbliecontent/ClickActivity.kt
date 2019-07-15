package com.jjmin.mbliecontent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import com.jjmin.mbliecontent.data.model.SendShapeData
import com.jjmin.mbliecontent.ui.select.UserSelectActivity
import com.jjmin.mbliecontent.util.SharedUtils.context
import com.jjmin.mbliecontent.util.TTSUtils
import kotlinx.android.synthetic.main.activity_click.*
import org.jetbrains.anko.toast

class ClickActivity : AppCompatActivity() {
    var tts = TTSUtils
    var recognizer: SpeechRecognizer? = null

    lateinit var list : ArrayList<SendShapeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click)
        list = intent.extras.getSerializable("list") as ArrayList<SendShapeData>
        clickLayout.setOnClickListener {
            tts.stop()
            startvoicd()
        }

        clickLayout.setOnLongClickListener {
            finish()
            return@setOnLongClickListener true
        }

    }

    fun startvoicd(){
        intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
        recognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizer!!.setRecognitionListener(listener)
        recognizer!!.startListening(intent)
    }

    //음성인식 리스너
    private val listener = object : RecognitionListener {
        //음성 인식 준비가 되었으면
        override fun onReadyForSpeech(bundle: Bundle) {

        }

        //입력이 시작되면
        override fun onBeginningOfSpeech() {

        }

        //입력 소리 변경 시
        override fun onRmsChanged(v: Float) {

        }

        //더 많은 소리를 받을 때
        override fun onBufferReceived(bytes: ByteArray) {

        }

        //음성 입력이 끝났으면
        override fun onEndOfSpeech() {

        }

        //에러가 발생하면
        override fun onError(i: Int) {
            tts.speak("읆성인식에 실패하였습니다. 다시 말해주세요")
        }

        //음성 인식 결과 받음
        override fun onResults(results: Bundle) {
            Log.e("음성인식", "성공")
            val key = SpeechRecognizer.RESULTS_RECOGNITION
            val result = results.getStringArrayList(key)
            val rs = arrayOfNulls<String>(result!!.size)
            result.toArray(rs)
            Log.e("rs", rs[0])
            (0 until list.size).forEach {
                if(list[it].name.contains(rs[0].toString())){
                    var intent = Intent(this@ClickActivity,UserSelectActivity::class.java)
                    intent.putExtra("name",list[it].name)
                    intent.putExtra("allergy",list[it].allergy)
                    intent.putExtra("material",list[it].material)
                    intent.putExtra("explain",list[it].explain)
                    intent.putExtra("country",list[it].country)
                    startActivity(intent)
                    finish()
                }
            }
//            if (rs[0] == "예" || rs[0] == "네" || rs[0] == "시작" || rs[0] == "시작하기") {
//                if (check == "Start") {
//                    context.startActivity<UserMainActivity>()
//                }
//            } else {
                tts.speak("읆성인식에 실패하였습니다. 다시 말해주세요")
//            }
        }

        //인식 결과의 일부가 유효할 때
        override fun onPartialResults(bundle: Bundle) {

        }

        //미래의 이벤트를 추가하기 위해 미리 예약되어진 함수
        override fun onEvent(i: Int, bundle: Bundle) {

        }
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            recognizer!!.setRecognitionListener(listener)
        }
    }

}
