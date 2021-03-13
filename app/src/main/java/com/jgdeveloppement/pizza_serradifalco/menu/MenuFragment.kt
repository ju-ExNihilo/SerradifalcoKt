package com.jgdeveloppement.pizza_serradifalco.menu

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentMenuBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel
import com.jgdeveloppement.pizza_serradifalco.viewmodel.ProductViewModel

class MenuFragment : Fragment() {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var productViewModel: ProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setTabListener()
        setTabLayout()
    }

    private fun setupViewModel() {
        productViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) ).get(
            ProductViewModel::class.java)
    }

    private fun getPizzaTomato(){
        productViewModel.getPizzaTomatoList().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.menuProgressLayout.visibility = View.GONE
                        resource.data?.let { productList -> binding.menuRecyclerView.adapter = MenuAdapter(context as HomeActivity, productList) }
                    }
                    Status.ERROR -> {
                        binding.menuProgressLayout.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.menuProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun setTabLayout(){
        val tabLayout = binding.tabLayoutMenu
        tabLayout.addTab(tabLayout.newTab().setText("Tomate"), 0, true)
        tabLayout.addTab(tabLayout.newTab().setText("Blanche"), 1, false)
        tabLayout.addTab(tabLayout.newTab().setText("Premium"), 2, false)
        tabLayout.addTab(tabLayout.newTab().setText("Dessert"), 3, false)
    }

    private fun setTabListener(){
        binding.tabLayoutMenu.addOnTabSelectedListener(object : OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(binding.tabLayoutMenu.selectedTabPosition){
                    0 -> getPizzaTomato()
                    1 -> Log.i("DEBUGGG", "Blanche")
                    2 -> Log.i("DEBUGGG", "Premium")
                    3 -> Log.i("DEBUGGG", "Dessert")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }
}
