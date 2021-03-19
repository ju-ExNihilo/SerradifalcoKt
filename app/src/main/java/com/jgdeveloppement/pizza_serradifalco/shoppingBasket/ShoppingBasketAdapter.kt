package com.jgdeveloppement.pizza_serradifalco.shoppingBasket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.models.ShoppingRow

class ShoppingBasketAdapter(private val shoppingRowList: List<ShoppingRow>, private val onShoppingRowClickListener: OnShoppingRowClickListener)
    : RecyclerView.Adapter<ShoppingBasketAdapter.ViewHolder>() {

    interface OnShoppingRowClickListener{
        fun onSpinnerQuantityChangeListener(position: Int, quantity: Int)
        fun onDeleteButtonClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_basket_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentShoppingRow = shoppingRowList[position]

        holder.itemName.text = currentShoppingRow.productName
        holder.itemSize.text = currentShoppingRow.size
        holder.itemPrice.text = "${currentShoppingRow.price} $"
        holder.itemExtra.text = currentShoppingRow.extra
        holder.spinnerQuantity.setSelection(currentShoppingRow.quantity-1)

        holder.spinnerQuantity.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                onShoppingRowClickListener.onSpinnerQuantityChangeListener(position, p2)
                holder.itemPrice.text = "${currentShoppingRow.price * (p2+1)} $"
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }


        holder.deleteButton.setOnClickListener { onShoppingRowClickListener.onDeleteButtonClicked(position) }
    }

    override fun getItemCount(): Int = shoppingRowList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val itemName : TextView = view.findViewById(R.id.shop_product_name)
        val itemSize : TextView = view.findViewById(R.id.shop_product_size)
        val itemPrice : TextView = view.findViewById(R.id.shop_product_price)
        val itemExtra : TextView = view.findViewById(R.id.shop_product_extra)
        val spinnerQuantity: Spinner = view.findViewById(R.id.shop_item_quantity_spinner)
        val deleteButton : ImageButton = view.findViewById(R.id.shop_product_delete_button)
    }
}