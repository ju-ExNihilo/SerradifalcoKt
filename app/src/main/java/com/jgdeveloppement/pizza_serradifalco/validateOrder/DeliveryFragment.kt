package com.jgdeveloppement.pizza_serradifalco.validateOrder

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.account.AddressAdapter
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentDeliveryBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Notification
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.utils.UserData
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel

class DeliveryFragment : Fragment() {

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        getAddressList()

        binding.deliveryDateEditText.setOnClickListener { datePicker() }
        binding.deliveryValidateButton.setOnClickListener { validateOrder() }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) ).get(
            MainViewModel::class.java)
    }

    private fun datePicker(){
        val newFragment = DatePickerFragment(requireActivity() as ValidateOrderActivity, binding.deliveryDateEditText)
        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun validateOrder(){
        val addressId = binding.radioGroupAddress.checkedRadioButtonId
        val address = binding.radioGroupAddress.findViewById<RadioButton>(addressId).text.toString()
        ValidateOrderActivity.finaliseOrder(activity, requireContext(), binding.deliveryDateEditText, binding.deliveryTimeSlotSpinner,
            binding.messageEditText, address, binding.deliveryDateError, binding.deliveryTimeError)
    }

    private fun getAddressList(){
        UserData.userId?.let { userId ->
            mainViewModel.getAllAddressByUserId(userId).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            binding.deliveryProgressLayout.visibility = View.GONE
                            resource.data?.let { addressList ->
                                for ( address in addressList){
                                    val radioButton = RadioButton(context)
                                    radioButton.text = "${address.wayNumber} ${address.way} - ${address.postCode}"
                                    radioButton.setTextColor(context!!.getColor(R.color.colorWhite))
                                    radioButton.buttonTintList = ColorStateList.valueOf(context!!.getColor(R.color.colorWhite))
                                    radioButton.textSize = 15F
                                    radioButton.tag = address.id
                                    binding.radioGroupAddress.addView(radioButton)
                                }
                                if (addressList.isNotEmpty()) binding.radioGroupAddress.check(1)

                            }
                        }
                        Status.ERROR -> {
                            binding.deliveryProgressLayout.visibility = View.GONE
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            binding.deliveryProgressLayout.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

}
