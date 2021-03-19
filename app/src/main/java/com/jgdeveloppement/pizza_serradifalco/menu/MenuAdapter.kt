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

class MenuAdapter(private val context: HomeActivity,
                  private val productList: List<Product>,
                  private val onProductShoppingClicked: OnProductShoppingClicked)
    : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    interface OnProductShoppingClicked{
        fun onClickedProductShop(productId: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]

        when(currentProduct.category) {
            "PREMIUM" -> {
                holder.itemName.setTextColor(context.getColor(R.color.colorGreen))
                holder.itemMiddlePrice.setTextColor(context.getColor(R.color.colorGreen))
                holder.itemLargePrice.setTextColor(context.getColor(R.color.colorGreen))
                holder.addButton.setImageResource(R.drawable.ic_shopping_premium)
            }
            "BLANCHE" -> {
                holder.itemName.setTextColor(context.getColor(R.color.colorBlanche))
                holder.itemMiddlePrice.setTextColor(context.getColor(R.color.colorBlanche))
                holder.itemLargePrice.setTextColor(context.getColor(R.color.colorBlanche))
                holder.addButton.setImageResource(R.drawable.ic_shopping_blanche)
            }
            "TOMATE" -> {
                holder.itemName.setTextColor(context.getColor(R.color.colorTomate))
                holder.itemMiddlePrice.setTextColor(context.getColor(R.color.colorTomate))
                holder.itemLargePrice.setTextColor(context.getColor(R.color.colorTomate))
                holder.addButton.setImageResource(R.drawable.ic_shopping_tomato)
            }
            "DESSERT" -> {
                holder.itemName.setTextColor(context.getColor(R.color.colorDessert))
                holder.itemMiddlePrice.setTextColor(context.getColor(R.color.colorDessert))
                holder.itemLargePrice.setTextColor(context.getColor(R.color.colorDessert))
                holder.addButton.setImageResource(R.drawable.ic_shopping_dessert)

            }
        }

        holder.itemName.text = currentProduct.name.replace("_", " ")
        holder.itemComponent.text = currentProduct.component
        holder.itemMiddlePrice.text = currentProduct.mediumPrice.toString() + " $"

        if (currentProduct.largePrice == 0.0){
            holder.itemLargePrice.text =  "N/A"
        }else{
            holder.itemLargePrice.text = currentProduct.largePrice.toString() + " $"
        }


        holder.addButton.setOnClickListener { onProductShoppingClicked.onClickedProductShop(currentProduct.id) }
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