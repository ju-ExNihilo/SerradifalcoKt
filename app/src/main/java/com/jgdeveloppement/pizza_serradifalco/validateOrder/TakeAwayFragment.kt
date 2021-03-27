package com.jgdeveloppement.pizza_serradifalco.validateOrder

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentTakeAwayBinding
import com.jgdeveloppement.pizza_serradifalco.utils.Notification
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class TakeAwayFragment : Fragment() {

    private var _binding: FragmentTakeAwayBinding? = null
    private val binding get() = _binding!!

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
        binding.takeAwayDateEditText.setOnClickListener { datePicker() }
        binding.takeAwayValidateButton.setOnClickListener { validateOrder() }
    }

    private fun datePicker(){
        val newFragment = DatePickerFragment(requireActivity() as ValidateOrderActivity, binding.takeAwayDateEditText)
        newFragment.show(requireActivity().supportFragmentManager, "datePicker")
    }

    private fun validateOrder(){
        val restaurant = if (binding.radioButtonSerradifalco.isChecked) "Serradifalco" else "Leonardo"
        ValidateOrderActivity.finaliseOrder(activity, requireContext(), binding.takeAwayDateEditText, binding.takeAwayTimeSlotSpinner,
            binding.takeAwayMessageEditText, restaurant, binding.takeAwayDateError, binding.takeAwayTimeError)

    }
}
