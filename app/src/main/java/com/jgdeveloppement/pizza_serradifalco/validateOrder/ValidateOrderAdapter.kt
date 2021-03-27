package com.jgdeveloppement.pizza_serradifalco.validateOrder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ValidateOrderAdapter(fm: FragmentManager, private val totalTabs: Int) : FragmentPagerAdapter(fm, totalTabs) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> TakeAwayFragment()
            1 -> DeliveryFragment()
            else -> TakeAwayFragment()
        }
    }

    override fun getCount(): Int = totalTabs

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "A Emporter"
            1 -> "Livraison"
            else -> "A Emporter"
        }
    }
}