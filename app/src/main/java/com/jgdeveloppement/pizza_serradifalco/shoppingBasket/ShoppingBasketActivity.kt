package com.jgdeveloppement.pizza_serradifalco.shoppingBasket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityShoppingBasketBinding
import com.jgdeveloppement.pizza_serradifalco.utils.UserData

class ShoppingBasketActivity : AppCompatActivity(), ShoppingBasketAdapter.OnShoppingRowClickListener {

    private lateinit var binding: ActivityShoppingBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initData()
        initTotal()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar(){
        setSupportActionBar(binding.shoppingBasketToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initData(){
        binding.shoppingBasketRecyclerView.adapter = ShoppingBasketAdapter(UserData.shoppingRowList, this)
    }

    private fun initTotal(){
        binding.shoppingBasketTotalPrice.text = getString(R.string.item_price, String.format("%.1f", UserData.getShopTotalPrice()))
    }

    companion object {
        /** Used to navigate to this activity  */
        fun navigate(activity: FragmentActivity?) {
            val intent = Intent(activity, ShoppingBasketActivity::class.java)
            ActivityCompat.startActivity(activity!!, intent, null)
        }
    }

    override fun onSpinnerQuantityChangeListener(position: Int, quantity: Int) {
        val shoppingRow = UserData.shoppingRowList[position]
        shoppingRow.quantity = quantity + 1
        UserData.shoppingRowList[position] = shoppingRow
        initTotal()
    }

    override fun onDeleteButtonClicked(position: Int) {
        UserData.shoppingRowList.removeAt(position)
        initData()
        initTotal()
    }
}
