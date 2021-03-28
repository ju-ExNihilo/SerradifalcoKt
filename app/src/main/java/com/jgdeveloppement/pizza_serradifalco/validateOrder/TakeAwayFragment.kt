package com.jgdeveloppement.pizza_serradifalco.validateOrder

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentTakeAwayBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Notification
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class TakeAwayFragment : Fragment() {

    private var _binding: FragmentTakeAwayBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentTakeAwayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        binding.takeAwayDateEditText.setOnClickListener { datePicker() }
        binding.takeAwayValidateButton.setOnClickListener { validateOrder() }
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService)) ).get(
            MainViewModel::class.java)
    }

    private fun datePicker(){
        val newFragment = DatePickerFragment(requireActivity() as ValidateOrderActivity, binding.takeAwayDateEditText)
        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun validateOrder(){
        val restaurant = if (binding.radioButtonSerradifalco.isChecked) "Serradifalco" else "Leonardo"
        ValidateOrderActivity.finaliseOrder(activity, binding.takeAwayProgressLayout, requireContext(), binding.takeAwayDateEditText, binding.takeAwayTimeSlotSpinner,
            binding.takeAwayMessageEditText, "false", "none", restaurant, binding.takeAwayDateError, binding.takeAwayTimeError, mainViewModel, viewLifecycleOwner)

    }
}
