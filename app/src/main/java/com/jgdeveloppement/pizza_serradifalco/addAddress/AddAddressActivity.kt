package com.jgdeveloppement.pizza_serradifalco.addAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityAddAddressBinding

class AddAddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
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

    companion object {
        /** Used to navigate to this activity  */
        fun navigate(activity: FragmentActivity?) {
            val intent = Intent(activity, AddAddressActivity::class.java)
            ActivityCompat.startActivity(activity!!, intent, null)
        }
    }
}
