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
import com.jgdeveloppement.pizza_serradifalco.injection.Injection
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.utils.Utils
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
            selectedName = requireArguments().getString(Utils.CARD_NAME)

        }
        setSelectedItem()
    }

    private fun setupViewModel() {
        productViewModel = ViewModelProviders.of(this, Injection.provideProductViewModelFactory()).get(ProductViewModel::class.java)
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
                        Utils.showSnackBar(binding.fragmentMenuLayout, getString(R.string.error_occurred))
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
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.nos_pizzas_tomate)) , 0, isSelectedItem(selectedItem, 0))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.nos_pizzas_blanche)), 1, isSelectedItem(selectedItem, 1))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.nos_pizzas_preniun)), 2, isSelectedItem(selectedItem, 2))
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.nos_desserts)), 3, isSelectedItem(selectedItem, 3))
    }

    private fun isSelectedItem(selectedItem: Int, position: Int): Boolean = selectedItem == position

    private fun setSelectedItem(){
        when(selectedName){
            getString(R.string.nos_pizzas_tomate) -> setTabLayout(0)
            getString(R.string.nos_pizzas_blanche) -> setTabLayout(1)
            getString(R.string.nos_pizzas_preniun) -> setTabLayout(2)
            getString(R.string.nos_desserts) -> setTabLayout(3)
            else -> setTabLayout(0)

        }
    }

    private fun setTabListener(){
        binding.tabLayoutMenu.addOnTabSelectedListener(object : OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(binding.tabLayoutMenu.selectedTabPosition){
                    0 -> getProductList(getString(R.string.nos_pizzas_tomate))
                    1 -> getProductList(getString(R.string.nos_pizzas_blanche))
                    2 -> getProductList(getString(R.string.nos_pizzas_preniun))
                    3 -> getProductList(getString(R.string.nos_desserts))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) { }

        })
    }

    override fun onClickedProductShop(productId: Int) {
        val navOptions: NavOptions = NavOptions.Builder().setPopUpTo(R.id.detailsFragment, true).build()
        val bundle = Bundle()
        bundle.putInt(Utils.PRODUCT_ID, productId)
        navController?.navigate(R.id.detailsFragment, bundle, navOptions)
    }
}
