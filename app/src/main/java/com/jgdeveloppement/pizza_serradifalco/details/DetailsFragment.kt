package com.jgdeveloppement.pizza_serradifalco.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentDetailsBinding
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentHomeBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.menu.MenuAdapter
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.viewmodel.ProductViewModel


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var productViewModel: ProductViewModel
    private var productId: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        if (arguments != null){
            productId = requireArguments().getInt("productId")
            initProduct(productId!!)
        }

    }

    private fun setupViewModel() {
        productViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) ).get(
            ProductViewModel::class.java)
    }

    private fun initProduct(id: Int){
        productViewModel.getPizzaById(id).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.detailsProgressLayout.visibility = View.GONE
                        resource.data?.let { product -> binding.textView2.text = product.name}
                    }
                    Status.ERROR -> {
                        binding.detailsProgressLayout.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.detailsProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

}
