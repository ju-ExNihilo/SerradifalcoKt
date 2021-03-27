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
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.textfield.TextInputEditText
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityValidateOrderBinding
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.utils.Notification
import com.jgdeveloppement.pizza_serradifalco.utils.UserData

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

        fun finaliseOrder(activity: FragmentActivity?, context: Context, dateEditText: TextInputEditText, timeSlotSpinner: Spinner, messageEditText: TextInputEditText,
                          whereGetOrder: String, dateError: TextView, timeError: TextView){
            val date = dateEditText.text.toString()
            val timeSlot = timeSlotSpinner.selectedItem.toString()
            val message = messageEditText.text.toString()

            if (date.isNotBlank() && timeSlot != "Créneaux Horaire"){
                Log.i("DEBUGGG", "whereGetOrder = $whereGetOrder date = $date timeSlot = $timeSlot message = $message")
                Notification.show(context)
                UserData.shoppingRowList.clear()
                activity?.finish()
            }else{
                if (date.isBlank()) dateError.visibility = View.VISIBLE else dateError.visibility = View.GONE
                if (timeSlot == "Créneaux Horaire")timeError.visibility = View.VISIBLE else timeError.visibility = View.GONE
            }
        }
    }
}
