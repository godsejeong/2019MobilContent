package com.jjmin.mbliecontent.ui.deployment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.estimote.sdk.BeaconManager
import com.estimote.sdk.Region
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.databinding.ActivityShapeDeploymentBinding
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.util.TTSUtils
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*
import com.estimote.sdk.SystemRequirementsChecker
import com.jjmin.mbliecontent.ui.select.UserSelectActivity
import com.jjmin.mbliecontent.util.SharedUtils

class ShapeDeploymentActivity : BaseActivity<ActivityShapeDeploymentBinding>() {
    var tts = TTSUtils
    override val LayoutId: Int = R.layout.activity_shape_deployment
    lateinit var beaconManager: BeaconManager
    lateinit var region : Region

    val useCase by lazy { DeploymentUseCase(this) }
    val viewmodel: DeploymentViewmodel by viewModel { parametersOf(useCase) }
    var ischeck = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewmodel
        beaconManager = BeaconManager(this)
        ischeck = true
        beaconManager.setRangingListener { p0, p1 ->
            if (p1!!.isNotEmpty()) {
                Log.e("ASdfasdf","ASdfasdf")
                var nearestBeacon = p1.get(0)
                Log.d("Airport", "Nearest places: " + nearestBeacon.rssi)
                if(nearestBeacon.rssi >= -65){
                    if(ischeck) {
                        var intent = Intent(this, UserSelectActivity::class.java)
                        intent.putExtra("name", SharedUtils.getFood(0))
                        intent.putExtra("allergy", SharedUtils.getAllergy(0))
                        intent.putExtra("material", SharedUtils.getMeterial(0))
                        intent.putExtra("explain", SharedUtils.getxplan(0))
                        intent.putExtra("country", SharedUtils.getCountry(0))
                        startActivity(intent)
                        ischeck = false
                    }
                }
            }
        }

        region = Region ("ranged region", UUID.fromString("74278BDA-B644-4520-8F0C-720EAF059935"), null, null)


    }

    override fun onResume() {
        super.onResume()
        ischeck = true
        SystemRequirementsChecker.checkWithDefaultDialogs(this)
        beaconManager.connect { beaconManager.startRanging(region) }
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
            overridePendingTransition(0, 0)

    }

}
