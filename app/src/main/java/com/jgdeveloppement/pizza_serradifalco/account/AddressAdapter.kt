package com.jgdeveloppement.pizza_serradifalco.account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.models.Address

class AddressAdapter(private val addressList: List<Address>, private val onClickDeleteButton: OnClickDeleteButton) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {

    interface OnClickDeleteButton{
        fun onDeleteButtonClicked(addressId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.address_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAddress = addressList[position]

        holder.itemNumberWay.text = currentAddress.wayNumber.toString()
        holder.itemWay.text = currentAddress.way
        holder.itemPostCode.text = currentAddress.postCode
        holder.itemTown.text = currentAddress.town

        if(currentAddress.additionalAddress == "none"){
            holder.itemAdditional.visibility = View.GONE
        }else{
            holder.itemAdditional.text = currentAddress.additionalAddress
        }

        holder.deleteButton.setOnClickListener { onClickDeleteButton.onDeleteButtonClicked(currentAddress.id) }

    }

    override fun getItemCount(): Int = addressList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val itemNumberWay : TextView = view.findViewById(R.id.address_item_number)
        val itemWay : TextView = view.findViewById(R.id.address_item_way)
        val itemPostCode : TextView = view.findViewById(R.id.address_item_post_code)
        val itemTown : TextView = view.findViewById(R.id.address_item_town)
        val itemAdditional : TextView = view.findViewById(R.id.address_item_additional)
        val deleteButton : ImageButton = view.findViewById(R.id.address_item_delete_button)
    }
}