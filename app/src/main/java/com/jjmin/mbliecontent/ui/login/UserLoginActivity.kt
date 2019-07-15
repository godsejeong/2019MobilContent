package com.jjmin.mbliecontent.ui.login

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.ui.base.BaseActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserLoginActivity : BaseActivity<com.jjmin.mbliecontent.databinding.ActivityUserLoginBinding>() {
    override val LayoutId = R.layout.activity_user_login
    private var mBluetoothAdapter: BluetoothAdapter? = null


    val useCase by lazy { LoginUseCase(this) }
    val viewmodel : LoginViewModel by viewModel { parametersOf(useCase) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding.vm = viewmodel

        viewmodel.userClicklogin.observe(this, Observer {
            var id = viewDataBinding.userLoginIdEt.text.toString()

            if(id.isNotEmpty())
                viewmodel.UserLogin(id)
            else
                toast("아이디를 입력해해세요")
        })

        fun PackageManager.missingSystemFeature(name: String): Boolean = !hasSystemFeature(name)
        packageManager.takeIf { it.missingSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE) }?.also {
            Toast.makeText(this, "이 기종은 BLE를 지원하지 않습니다.", Toast.LENGTH_SHORT)
            finish()
        }

        val bluetoothManager : BluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter


        if (mBluetoothAdapter == null || !mBluetoothAdapter?.isEnabled!!) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE).also {
                startActivityForResult(it, 100)
            }
        }

        val permissionAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        val permissionLocation = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
        if(permissionAudio == PackageManager.PERMISSION_DENIED || permissionLocation == PackageManager.PERMISSION_DENIED){
            permissionCheck()
        }else{

        }

    }

    fun permissionCheck(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.ACCESS_FINE_LOCATION),
                100)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED && grantResults[1] === PackageManager.PERMISSION_GRANTED) {
                // 권한 허가
                Toast.makeText(this, "권한을 허용하였습니다.", Toast.LENGTH_SHORT)
            } else {
                // 권한 거부
                Toast.makeText(this, "앱을 사용하시려면 권한을 승락해 주세요.", Toast.LENGTH_SHORT)
            }
            return
        }
    }
}
