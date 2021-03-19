package com.jgdeveloppement.pizza_serradifalco.menu

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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentMenuBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel
import com.jgdeveloppement.pizza_serradifalco.viewmodel.ProductViewModel

class MenuFragment : Fragment(), MenuAdapter.OnProductShoppingClicked {

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var productViewModel: ProductViewModel
    private var navController: NavController? = null
    private var selectedName: String? = null

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
        navController = Navigation.findNavController(view)
        setupViewModel()
        setTabListener()
        if (arguments != null){
            selectedName = requireArguments().getString("cardName")

        }
        setSelectedItem()
    }

    private fun setupViewModel() {
        productViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) ).get(
            ProductViewModel::class.java)
    }

    private fun getProductList(type: String){
        productViewModel.getProductList(type).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.menuProgressLayout.visibility = View.GONE
                        resource.data?.let { productList -> binding.menuRecyclerView.adapter = MenuAdapter(context as HomeActivity, productList, this) }
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

    private fun setTabLayout(selectedItem: Int){
        val tabLayout = binding.tabLayoutMenu
        tabLayout.addTab(tabLayout.newTab().setText("Tomate") , 0, isSelectedItem(selectedItem, 0))
        tabLayout.addTab(tabLayout.newTab().setText("Blanche"), 1, isSelectedItem(selectedItem, 1))
        tabLayout.addTab(tabLayout.newTab().setText("Premium"), 2, isSelectedItem(selectedItem, 2))
        tabLayout.addTab(tabLayout.newTab().setText("Dessert"), 3, isSelectedItem(selectedItem, 3))
    }

    private fun isSelectedItem(selectedItem: Int, position: Int): Boolean = selectedItem == position

    private fun setSelectedItem(){
        when(selectedName){
            "Tomate" -> setTabLayout(0)
            "Blanche" -> setTabLayout(1)
            "Premium" -> setTabLayout(2)
            "Dessert" -> setTabLayout(3)
            else -> setTabLayout(0)

        }
    }

    private fun setTabListener(){
        binding.tabLayoutMenu.addOnTabSelectedListener(object : OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(binding.tabLayoutMenu.selectedTabPosition){
                    0 -> getProductList("Tomate")
                    1 -> getProductList("Blanche")
                    2 -> getProductList("Prenium")
                    3 -> getProductList("Dessert")
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
    }

    override fun onClickedProductShop(productId: Int) {
        val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detailsFragment, true).build()
        val bundle = Bundle()
        bundle.putInt("productId", productId)
        navController?.navigate(R.id.detailsFragment, bundle, navOptions)
    }
}
