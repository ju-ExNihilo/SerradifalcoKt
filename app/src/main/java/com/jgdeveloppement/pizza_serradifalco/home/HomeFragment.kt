package com.jgdeveloppement.pizza_serradifalco.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.jgdeveloppement.pizza_serradifalco.R
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
import com.jgdeveloppement.pizza_serradifalco.utils.UserData

class HomeFragment : Fragment(), MenuCardAdapter.OnCardMenuClicked {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private var navController: NavController? = null

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
        navController = Navigation.findNavController(view)
        setupViewModel()
        initCardMenu()
        initPizzaDay()
        testUser()
        Log.i("DEBUGGG", "firstname : ${UserData.userFirstName} lastname : ${UserData.userLastName} userId : ${UserData.userId} phone : ${UserData.userPhone}")
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
        FirebaseAuth.getInstance().currentUser?.email?.let { it ->
            mainViewModel.getUser(it).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.homeProgressLayout.visibility = View.GONE
                            resource.data?.let { user ->
                                UserData.userFirstName = user.firstName
                                UserData.userLastName = user.lastName
                                UserData.userId = user.id
                                UserData.userPhone = user.phone
                            }
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
        binding.homePizzaDay.setOnClickListener { navigateToPizzaDay(pizzaDay.id) }
        binding.homePizzaDayArrow.setOnClickListener { navigateToPizzaDay(pizzaDay.id) }
    }

    private fun navigateToPizzaDay(pizzaId: Int){
        val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detailsFragment, true).build()
        val bundle = Bundle()
        bundle.putInt("productId", pizzaId)
        navController?.navigate(R.id.detailsFragment, bundle, navOptions)
    }

    private fun initMenuCard(setting: Settings){
        val menuCardList = listOf(HomeMenuCard("Premium", setting.premiumUrl), HomeMenuCard("Tomate", setting.tomatoUrl),
                HomeMenuCard("Blanche", setting.blancheUrl),HomeMenuCard("Dessert", setting.dessert1Url))
        binding.homeRecyclerView.adapter = MenuCardAdapter(context as HomeActivity, menuCardList, this)
    }

    override fun onClickedCardMenu(cardName: String) {
        val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.menuFragment, true).build()
        val bundle = Bundle()
        bundle.putString("cardName", cardName)
        navController?.navigate(R.id.menuFragment, bundle, navOptions)
    }

}
