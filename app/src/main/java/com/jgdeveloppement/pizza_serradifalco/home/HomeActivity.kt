package com.jgdeveloppement.pizza_serradifalco.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityHomeBinding
import com.jgdeveloppement.pizza_serradifalco.login.LoginActivity
import com.jgdeveloppement.pizza_serradifalco.shoppingBasket.ShoppingBasketActivity
import com.jgdeveloppement.pizza_serradifalco.utils.UserData
import com.mikepenz.actionitembadge.library.ActionItemBadge
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome

class HomeActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHomeBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)
        configureDrawerLayout()
        binding.navView.setupWithNavController(navController!!)
        binding.bottomNavigationView.setupWithNavController(navController!!)
        configureNavigation()
        updateUiForSomeDestination()
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    //  Configure ToolBar with badge
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        ActionItemBadge.update(this, menu?.findItem(R.id.shopping), FontAwesome.Icon.faw_shopping_basket, ActionItemBadge.BadgeStyles.GREEN, UserData.getNumberProduct())
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.shopping) {
            if (UserData.getNumberProduct() > 0){
                ShoppingBasketActivity.navigate(this)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    //  Configure Drawer Layout
    private fun configureDrawerLayout() {
        val toggle = ActionBarDrawerToggle(this, binding.layoutDrawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.layoutDrawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if (binding.layoutDrawer.isDrawerOpen(GravityCompat.START)) binding.layoutDrawer.closeDrawer(GravityCompat.START) else super.onBackPressed()
    }

    //  Configure Bottom navigation
    private fun configureNavigation(){
        val logout = binding.navView.menu.findItem(R.id.logout)
        logout.setOnMenuItemClickListener {
            AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener { finish(); LoginActivity.navigate(this) }
            true
        }

        val call = binding.bottomNavigationView.menu.findItem(R.id.call)
        call.setOnMenuItemClickListener {
            Log.i("DEBUGGG", "Start call Intent")
            true
        }
    }

    private fun updateUiForSomeDestination(){
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailsFragment || destination.id == R.id.accountFragment || destination.id == R.id.pizzeriaFragment){
                binding.bottomNavigationView.visibility = View.GONE
            }else{
                binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        /** Used to navigate to this activity  */
        fun navigate(activity: FragmentActivity?) {
            val intent = Intent(activity, HomeActivity::class.java)
            ActivityCompat.startActivity(activity!!, intent, null)
            activity.finish()
        }
    }
}
