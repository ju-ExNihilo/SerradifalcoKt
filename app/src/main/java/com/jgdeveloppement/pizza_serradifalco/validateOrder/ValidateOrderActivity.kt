package com.jgdeveloppement.pizza_serradifalco.validateOrder

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityValidateOrderBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Notification
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.utils.UserData
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel
import org.json.JSONArray

class ValidateOrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityValidateOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValidateOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        setTabLayout()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar(){
        setSupportActionBar(binding.validateOrderToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setTabLayout(){
        binding.validateOrderViewPager.adapter = ValidateOrderAdapter(supportFragmentManager, 2)
        binding.tabLayoutValidateOrder.setupWithViewPager(binding.validateOrderViewPager)
    }


    companion object {

        /** Used to navigate to this activity  */
        fun navigate(activity: FragmentActivity?) {
            val intent = Intent(activity, ValidateOrderActivity::class.java)
            ActivityCompat.startActivity(activity!!, intent, null)
            activity.finish()
        }

        fun finaliseOrder(activity: FragmentActivity?, constraintLayout: ConstraintLayout, context: Context, date: String,
                          timeSlot: String, message: String, isDelivery: String, addressAdditional: String,
                          whereGetOrder: String, dateError: TextView, timeError: TextView, mainViewModel: MainViewModel, lifecycleOwner: LifecycleOwner){

            if (date.isNotBlank() && timeSlot != "Créneaux Horaire"){

                val map = HashMap<String, String>()
                map["user_id"] = UserData.userId.toString()
                map["order_date"] = date.toString()
                map["order_time"] = timeSlot
                map["is_delivery"] = isDelivery
                map["order_price"] = UserData.getShopTotalPrice().toString()
                map["address"] = whereGetOrder
                map["address_additional"] = addressAdditional
                map["order_message"] = message

                insertNewOrder(activity, context, constraintLayout, mainViewModel, map, lifecycleOwner)

            }else{
                if (date.isBlank()) dateError.visibility = View.VISIBLE else dateError.visibility = View.GONE
                if (timeSlot == "Créneaux Horaire")timeError.visibility = View.VISIBLE else timeError.visibility = View.GONE
            }
        }

        private fun insertNewOrder(activity: FragmentActivity?, context: Context, constraintLayout: ConstraintLayout,
                                   mainViewModel: MainViewModel, order: HashMap<String, String>, lifecycleOwner: LifecycleOwner){

            mainViewModel.insertNewOrder(order).observe(lifecycleOwner,{
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            it.data?.let { it1 -> insertShoppingRow(activity, context, constraintLayout, it1, mainViewModel, lifecycleOwner)}
                        }
                        Status.ERROR -> { Log.i("DEBUGGG", it.message) }
                        Status.LOADING -> { constraintLayout.visibility = View.VISIBLE }
                    }
                }
            })
        }



        private fun insertShoppingRow(activity: FragmentActivity?, context: Context,
                                      constraintLayout: ConstraintLayout, orderId: Int, mainViewModel: MainViewModel, lifecycleOwner: LifecycleOwner){

            Log.i("DEBUGGG", UserData.shoppingRowList.toString())
            mainViewModel.insertShoppingRow(orderId, UserData.shoppingRowList.toString()).observe(lifecycleOwner,{
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            it.data?.let { shop -> Log.i("DEBUGGG", shop.toString())
                                Notification.show(context)
                                UserData.shoppingRowList.clear()
                                activity?.finish()
                            }
                        }
                        Status.ERROR -> { Log.i("DEBUGGG", it.message) }
                        Status.LOADING -> { constraintLayout.visibility = View.VISIBLE }
                    }
                }
            })
        }
    }
}
