package com.jgdeveloppement.pizza_serradifalco.account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.jgdeveloppement.pizza_serradifalco.addAddress.AddAddressActivity
import com.jgdeveloppement.pizza_serradifalco.databinding.FragmentAccountBinding
import com.jgdeveloppement.pizza_serradifalco.factory.ViewModelFactory
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.injection.Injection
import com.jgdeveloppement.pizza_serradifalco.menu.MenuAdapter
import com.jgdeveloppement.pizza_serradifalco.models.Address
import com.jgdeveloppement.pizza_serradifalco.retrofit.ApiHelper
import com.jgdeveloppement.pizza_serradifalco.retrofit.Resource
import com.jgdeveloppement.pizza_serradifalco.retrofit.RetrofitBuilder
import com.jgdeveloppement.pizza_serradifalco.utils.Status
import com.jgdeveloppement.pizza_serradifalco.utils.UserData
import com.jgdeveloppement.pizza_serradifalco.viewmodel.MainViewModel
import com.jgdeveloppement.pizza_serradifalco.viewmodel.ProductViewModel


class AccountFragment : Fragment(), AddressAdapter.OnClickDeleteButton, UserPopup.OnClickValidateButton {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel

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
        setupViewModel()

        binding.accountAddAddressButton.setOnClickListener { AddAddressActivity.navigate(activity) }
        binding.accountUpdateButton.setOnClickListener { UserPopup(context, this).show() }
    }

    override fun onResume() {
        super.onResume()
        getAddressList()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(this, Injection.provideMainViewModelFactory()).get(MainViewModel::class.java)
    }

    private fun getAddressList(){
        UserData.userId?.let { userId -> getListAddressFromLiveData(mainViewModel.getAllAddressByUserId(userId)) }
    }

    private fun initUserData(){
        binding.accountFirstName.text = UserData.userFirstName
        binding.accountLastName.text = UserData.userLastName
        binding.accountPhone.text = UserData.userPhone
    }

    override fun onDeleteButtonClicked(addressId: Int) {
        UserData.userId?.let { userId -> getListAddressFromLiveData(mainViewModel.deleteAddress(addressId, userId)) }
    }

    private fun getListAddressFromLiveData(addressList: LiveData<Resource<List<Address>>>) {
        addressList.observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.accountProgressLayout.visibility = View.GONE
                        resource.data?.let { addressList -> binding.addressRecyclerView.adapter = AddressAdapter(addressList, this)}
                    }
                    Status.ERROR -> {
                        binding.accountProgressLayout.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.accountProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    override fun onClickedValidateButton(firstName: String, lastName: String, phone: String) {
        val map = HashMap<String, String>()
        map["user_firstname"] = firstName
        map["user_lastname"] = lastName
        map["user_email"] = UserData.userMail.toString()
        map["user_tel"] = phone
        map["user_id"] = UserData.userId.toString()

        mainViewModel.updateUser(map).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.accountProgressLayout.visibility = View.GONE
                        resource.data?.let { user ->
                            UserData.userFirstName = user.firstName
                            UserData.userLastName = user.lastName
                            UserData.userId = user.id
                            UserData.userPhone = user.phone
                            UserData.userMail = user.email
                            initUserData()
                        }
                    }
                    Status.ERROR -> {
                        binding.accountProgressLayout.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.accountProgressLayout.visibility = View.VISIBLE
                    }
                }
            }
        })

    }

}
