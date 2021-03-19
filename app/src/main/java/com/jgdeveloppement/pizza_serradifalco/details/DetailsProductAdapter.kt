package com.jgdeveloppement.pizza_serradifalco.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.models.Product

class DetailsProductAdapter(private val context: HomeActivity,
                            private val productList: List<Product>,
                            private val layoutId: Int,
                            private val isLarge: Boolean,
                            private val onSelectOtherProductListener: OnSelectOtherProductListener)
    : RecyclerView.Adapter<DetailsProductAdapter.ViewHolder>() {

    interface OnSelectOtherProductListener{
        fun onSpinnerChangeListener(product: Product, quantity: Int)
        fun onCheckBoxSelectListener(product: Product, isLarge: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]
        val price = if(isLarge) currentProduct.largePrice else currentProduct.mediumPrice

        holder.itemName.text = currentProduct.name.replace("_", " ")
        holder.itemPrice?.text = context.getString(R.string.item_price, String.format("%.1f", currentProduct.mediumPrice))
        holder.itemExtraPrice?.text = context.getString(R.string.item_price, String.format("%.1f", price))

        holder.spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val item : String = p0?.adapter?.getItem(p2).toString()
                onSelectOtherProductListener.onSpinnerChangeListener(currentProduct, item.toInt())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) { }
        }

        holder.checkBox?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                onSelectOtherProductListener.onCheckBoxSelectListener(currentProduct, isLarge)
            }
        }

    }

    override fun getItemCount(): Int = productList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val itemName : TextView = view.findViewById(R.id.details_item_name)
        val itemPrice : TextView? = view.findViewById(R.id.details_item_price)
        val itemExtraPrice : TextView? = view.findViewById(R.id.details_extra_item_price)
        val spinner: Spinner? = view.findViewById(R.id.details_item_spinner)
        val checkBox: CheckBox? = view.findViewById(R.id.details_extra_check_box_item)
    }
}