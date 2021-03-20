package com.jgdeveloppement.pizza_serradifalco.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jgdeveloppement.pizza_serradifalco.addAddress.AddAddressActivity
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentAccountBinding
import com.jgdeveloppement.pizza_serradifalco.utils.UserData


class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUserData()

        binding.accountAddAddressButton.setOnClickListener { AddAddressActivity.navigate(activity) }
    }

    private fun initUserData(){
        binding.accountFirstName.text = UserData.userFirstName
        binding.accountLastName.text = UserData.userLastName
        binding.accountPhone.text = UserData.userPhone
    }

}
