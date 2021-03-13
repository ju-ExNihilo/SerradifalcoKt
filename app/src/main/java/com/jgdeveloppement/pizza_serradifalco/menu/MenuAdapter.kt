package com.jgdeveloppement.pizza_serradifalco.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.home.HomeActivity
import com.jgdeveloppement.pizza_serradifalco.models.Product

class MenuAdapter(private val context: HomeActivity, private val productList: List<Product>): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]

        holder.itemName.text = currentProduct.name
        holder.itemComponent.text = currentProduct.component
        holder.itemMiddlePrice.text = currentProduct.mediumPrice.toString() + " $"
        holder.itemLargePrice.text = currentProduct.largePrice.toString() + " $"
    }

    override fun getItemCount(): Int = productList.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val itemName : TextView = view.findViewById(R.id.menu_item_name)
        val itemComponent : TextView = view.findViewById(R.id.menu_item_component)
        val itemMiddlePrice : TextView = view.findViewById(R.id.menu_item_middle_price)
        val itemLargePrice : TextView = view.findViewById(R.id.menu_item_large_price)
        val addButton : ImageButton = view.findViewById(R.id.menu_item_add_button)
    }
}