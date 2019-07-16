package com.jjmin.mbliecontent.ui.blind

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.databinding.ActivityBlindUserBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.ui.select.UserSelectActivity
import com.jjmin.mbliecontent.util.SharedUtils
import com.jjmin.mbliecontent.util.TTSUtils
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*
import com.google.gson.Gson
import org.altbeacon.beacon.*
import android.os.RemoteException


class BlindUserActivity : BaseActivity<ActivityBlindUserBinding>() , BeaconConsumer {

    var tts = TTSUtils

    lateinit var beaconManager: BeaconManager
    override val LayoutId: Int = R.layout.activity_blind_user

    val useCase by lazy { BlindUseCase(this) }
    val viewmodel : BlindViewModel by viewModel { parametersOf(useCase) }
    var ischeck = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ischeck = true

        viewDataBinding.vm = viewmodel

        beaconManager = BeaconManager.getInstanceForApplication(this)

        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"))

        beaconManager.bind(this)
    }

    override fun onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(object : RangeNotifier {
            override fun didRangeBeaconsInRegion(p0: MutableCollection<Beacon>?, p1: org.altbeacon.beacon.Region?) {
                if (p0?.size!! > 0) {
                    for (beacon in p0) {
                        Log.e("asdf",Gson().toJson(beacon))
                        "00:13:AA:00:11:D9"
                        "00:13:AA:00:09:6A"

                        if(beacon.bluetoothAddress == "00:13:AA:00:11:D9") {
                            if (beacon.rssi >= -65) {
                                if (ischeck) {
                                    var intent = Intent(this@BlindUserActivity, UserSelectActivity::class.java)
                                    intent.putExtra("name", SharedUtils.getFood(0))
                                    intent.putExtra("allergy", SharedUtils.getAllergy(0))
                                    intent.putExtra("material", SharedUtils.getMeterial(0))
                                    intent.putExtra("explain", SharedUtils.getxplan(0))
                                    intent.putExtra("country", SharedUtils.getCountry(0))
                                    startActivity(intent)
                                    ischeck = false
                                }
                            }
                        }else if(beacon.bluetoothAddress == "00:13:AA:00:09:6A"){
                            if (beacon.rssi >= -65) {
                                tts.speak("장애물이 주변에 있습니다.")
                                toast("장애물이 주변에 있습니다.")
                            }
                        }

                    }
                }
            }
        })

        try {
            beaconManager.startRangingBeaconsInRegion(org.altbeacon.beacon.Region("myRangingUniqueId", null, null, null))
        } catch (e: RemoteException) {
        }

    }

    override fun onResume() {
        super.onResume()
        ischeck = true
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            tts.stop()
            overridePendingTransition(0, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.stop()
        beaconManager.unbind(this)
        overridePendingTransition(0, 0)
    }
}
