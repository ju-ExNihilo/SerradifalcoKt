package com.jgdeveloppement.pizza_serradifalco.addAddress

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityAddAddressBinding
import com.jgdeveloppement.pizza_serradifalco.injection.Injection
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.utils.UserData
import com.jgdeveloppement.pizza_serradifalco.utils.Utils
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
        mainViewModel = ViewModelProviders.of(this, Injection.provideMainViewModelFactory()).get(MainViewModel::class.java)
    }

    private fun addAddress(){
        binding.addAddressValidateButton.setOnClickListener {

            val numberWay = binding.numberWayEditText.text.toString()
            val way = binding.wayEditText.text.toString()
            val postcode = binding.postCodeSpinner.selectedItem.toString()
            var additional = binding.additionalEditText.text.toString()
            additional = if (additional.isBlank()) Utils.NONE else additional

            if (numberWay.isNotBlank() && way.isNotBlank() && postcode.isNotBlank()){
                if (postcode == Utils.POST_CODE_83160 || postcode == Utils.POST_CODE_83000 || postcode == Utils.POST_CODE_83100 || postcode == Utils.POST_CODE_83130){
                    val town = when(postcode){
                        Utils.POST_CODE_83160 -> getString(R.string.la_valette)
                        Utils.POST_CODE_83130 -> getString(R.string.la_garde)
                        else -> getString(R.string.toulon)
                    }
                    UserData.userId?.let { it1 -> insertNewAddress(numberWay.toInt(), way, postcode, town, additional, it1) }
                }else{
                    binding.postCodeError.visibility = View.VISIBLE
                }
            }else{
                Utils.setVisibilityError(numberWay, binding.numberWayError)
                Utils.setVisibilityError(way, binding.wayError)
                Utils.setVisibilityError(postcode, binding.postCodeError)
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
                    Status.ERROR -> {
                        binding.addAddressProgressLayout.visibility = View.GONE
                        Utils.showSnackBar(binding.addAddressLayout, getString(R.string.error_occurred))
                    }
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
