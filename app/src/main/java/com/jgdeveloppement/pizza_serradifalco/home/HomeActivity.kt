package com.jgdeveloppement.pizza_serradifalco.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.firebase.ui.auth.AuthUI
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.ActivityHomeBinding
import com.jgdeveloppement.pizza_serradifalco.login.LoginActivity
import com.mikepenz.actionitembadge.library.ActionItemBadge
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding
    private var navController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setSupportActionBar(binding.toolbar)
        configureDrawerLayout()
        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        ActionItemBadge.update(this, menu?.findItem(R.id.shopping), FontAwesome.Icon.faw_shopping_basket, ActionItemBadge.BadgeStyles.GREEN, 2)
        return super.onCreateOptionsMenu(menu)
    }

    // 2 - Configure Drawer Layout
    private fun configureDrawerLayout() {
        val toggle = ActionBarDrawerToggle(this, binding.layoutDrawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.layoutDrawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.accueil -> Log.i("DEBUGGG", "accueil")
            R.id.menu -> Log.i("DEBUGGG", "menu")
            R.id.pizzeria -> Log.i("DEBUGGG", "pizzeria")
            R.id.logout -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnSuccessListener { finish(); LoginActivity.navigate(this) }

            }
        }
        binding.layoutDrawer.closeDrawer(GravityCompat.START)
        return true
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
