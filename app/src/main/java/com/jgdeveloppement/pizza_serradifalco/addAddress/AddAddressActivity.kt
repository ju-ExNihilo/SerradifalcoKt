package com.jgdeveloppement.pizza_serradifalco.addAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityAddAddressBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.utils.UserData
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel

class AddAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAddressBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        initToolbar()

        addAddress()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar(){
        setSupportActionBar(binding.addAddressToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) ).get(
            MainViewModel::class.java)
    }

    private fun addAddress(){
        binding.addAddressValidateButton.setOnClickListener {

            val numberWay = binding.numberWayEditText.text.toString()
            val way = binding.wayEditText.text.toString()
            val postcode = binding.postCodeSpinner.selectedItem.toString()
            var additional = binding.additionalEditText.text.toString()
            additional = if (additional.isBlank()) "none" else additional

            if (numberWay.isNotBlank() && way.isNotBlank() && postcode.isNotBlank()){
                if (postcode == "83160" || postcode == "83000" || postcode == "83100" || postcode == "83130"){
                    val town = when(postcode){
                        "83160" -> "La Valette"
                        "83130" -> "La Garde"
                        else -> "Toulon"
                    }
                    UserData.userId?.let { it1 -> insertNewAddress(numberWay.toInt(), way, postcode, town, additional, it1) }
                }else{
                    binding.postCodeError.text = "Merci de choisir un code postale dans la liste"
                    binding.postCodeError.visibility = View.VISIBLE
                }
            }else{
                if (numberWay.isBlank()) binding.numberWayError.visibility = View.VISIBLE else binding.numberWayError.visibility = View.GONE
                if (way.isBlank()) binding.wayError.visibility = View.VISIBLE else binding.wayError.visibility = View.GONE
                if (postcode.isBlank()) binding.postCodeError.visibility = View.VISIBLE else binding.postCodeError.visibility = View.GONE
            }


        }
    }

    private fun insertNewAddress(wayNumber : Int, way : String,  postCode : String, town : String, additionalAddress : String, userId : Int) {
        mainViewModel.insertAddress(wayNumber, way, postCode, town, additionalAddress, userId).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        finish()
                    }
                    Status.ERROR -> {}
                    Status.LOADING -> {
                        binding.addAddressProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }


    companion object {
        /** Used to navigate to this activity  */
        fun navigate(activity: FragmentActivity?) {
            val intent = Intent(activity, AddAddressActivity::class.java)
            ActivityCompat.startActivity(activity!!, intent, null)
        }
    }
}
