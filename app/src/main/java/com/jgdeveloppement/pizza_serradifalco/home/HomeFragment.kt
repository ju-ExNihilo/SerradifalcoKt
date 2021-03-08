package com.jgdeveloppement.pizza_serradifalco.home

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.jgdeveloppement.pizza_serradifalco.addUser.AddUserActivity
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel

import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentHomeBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.models.HomeMenuCard
import com.jgdeveloppement.pizza_serradifalco.models.Product
import com.jgdeveloppement.pizza_serradifalco.models.Settings
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Status

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        initCardMenu()
        initPizzaDay()
        testUser()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) ).get(
            MainViewModel::class.java)
    }

    private fun initCardMenu() {
        mainViewModel.getSettings().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.homeProgressLayout.visibility = View.GONE
                        resource.data?.let { setting -> initMenuCard(setting) }
                    }
                    Status.ERROR -> {
                        binding.homeProgressLayout.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.homeProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun initPizzaDay(){
        mainViewModel.getPizzaDay().observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.homeProgressLayout.visibility = View.GONE
                        resource.data?.let { pizzaDay -> initPizzaDayView(pizzaDay) }
                    }
                    Status.ERROR -> {
                        binding.homeProgressLayout.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.homeProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun testUser(){
        FirebaseAuth.getInstance().currentUser?.email?.let {
            mainViewModel.getUser(it).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.homeProgressLayout.visibility = View.GONE
                            resource.data?.let { user -> Toast.makeText(context, user.email, Toast.LENGTH_LONG).show() }
                        }
                        Status.ERROR -> {
                            binding.homeProgressLayout.visibility = View.GONE
                            AddUserActivity.navigate(this.activity)
                        }
                        Status.LOADING -> {
                            binding.homeProgressLayout.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

    private fun initPizzaDayView(pizzaDay: Product){
        Glide.with(this).load(Uri.parse(pizzaDay.urlPic)).into(binding.homePizzaDayImage)
        binding.homePizzaDayName.text = pizzaDay.name
        binding.homePizzaDayComponent.text = pizzaDay.component

    }

    private fun initMenuCard(setting: Settings){
        val menuCardList = listOf(HomeMenuCard("Premium", setting.premiumUrl), HomeMenuCard("Tomate", setting.tomatoUrl),
                HomeMenuCard("Blanche", setting.blancheUrl),HomeMenuCard("Dessert", setting.dessert1Url))
        binding.homeRecyclerView.adapter = MenuCardAdapter(context as HomeActivity, menuCardList)
    }

}
