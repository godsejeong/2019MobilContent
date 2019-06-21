package com.jjmin.mbliecontent.ui.food

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.jjmin.mbliecontent.R
import com.jjmin.mbliecontent.ui.base.BaseActivity
import com.jjmin.mbliecontent.util.SharedUtils
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class FoodInfoActivity : BaseActivity<com.jjmin.mbliecontent.databinding.ActivityFoodInfoBinding>() {

    override val LayoutId: Int = R.layout.activity_food_info

    val useCase by lazy { FoodInfoUseCase(this) }
    val viewmodel: FoodInfoViewModel by viewModel { parametersOf(useCase) }
    var id : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewmodel
        id = intent.getIntExtra("id",0)
        viewDataBinding.foodNameEt.setText( SharedUtils.getFood(id))
        viewDataBinding.foodCountryNameEt.setText( SharedUtils.getCountry(id))
        viewDataBinding.foodExplanEt.setText( SharedUtils.getxplan(id))
        viewDataBinding.foodAllergyEt.setText( SharedUtils.getAllergy(id))
        viewDataBinding.foodMeterialEt.setText( SharedUtils.getMeterial(id))

        viewmodel.clickComplete.observe(this, Observer {
            var foodName = viewDataBinding.foodNameEt.text.toString()
            var foodCountry = viewDataBinding.foodCountryNameEt.text.toString()
            var foodExplan = viewDataBinding.foodExplanEt.text.toString()
            var foodAllergy = viewDataBinding.foodAllergyEt.text.toString()
            var foodMeterial = viewDataBinding.foodMeterialEt.text.toString()

            if(foodName.isNotEmpty() || foodCountry.isNotEmpty() || foodExplan.isNotEmpty() || foodAllergy.isNotEmpty() || foodMeterial.isNotEmpty()){
                SharedUtils.foodShared(id)
                SharedUtils.saveFood(foodName,foodCountry,foodExplan,foodAllergy,foodMeterial)
                finish()
            }
        })
    }
}
