package com.jgdeveloppement.pizza_serradifalco.addUser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityAddUserBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.utils.UserData
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        addUser()
    }

    private fun addUser(){
        binding.validateButton.setOnClickListener{
            val map = HashMap<String, String>()
            val firstName = binding.firstNameEditText.text.toString()
            val lastName = binding.lastnameEditText.text.toString()
            val email = FirebaseAuth.getInstance().currentUser?.email ?: "none"
            val password = binding.passwordEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()

            if (firstName.isNotBlank() && lastName.isNotBlank() && password.isNotBlank() && phone.isNotBlank()){
                map["user_lastname"] = lastName
                map["user_firstname"] = firstName
                map["user_password"] = password
                map["user_email"] = email
                map["user_tel"] = phone
                insertNewUser(map)

            }else{
                if (firstName.isBlank()) binding.firstnameError.visibility = View.VISIBLE else binding.firstnameError.visibility = View.GONE
                if (lastName.isBlank()) binding.lastnameError.visibility = View.VISIBLE else binding.lastnameError.visibility = View.GONE
                if (password.isBlank()) binding.passwordError.visibility = View.VISIBLE else binding.passwordError.visibility = View.GONE
                if (phone.isBlank()) binding.phoneError.visibility = View.VISIBLE else binding.phoneError.visibility = View.GONE
            }
        }
    }

    private fun insertNewUser(map: HashMap<String, String>){
        mainViewModel.createUser(map).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        it.data?.let { it1 ->
                            UserData.userFirstName = it1.firstName
                            UserData.userLastName = it1.lastName
                            UserData.userId = it1.id
                            UserData.userPhone = it1.phone
                            HomeActivity.navigate(this)
                        }
                    }
                    Status.ERROR -> {
                        Log.i("DEBUGGG", it.message)
                    }
                    Status.LOADING -> {
                        binding.addUserProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) ).get(
            MainViewModel::class.java)
    }

    companion object {
        /** Used to navigate to this activity  */
        fun navigate(activity: FragmentActivity?) {
            val intent = Intent(activity, AddUserActivity::class.java)
            ActivityCompat.startActivity(activity!!, intent, null)
            activity.finish()
        }
    }
}
