package com.jgdeveloppement.pizza_serradifalco.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.models.HomeMenuCard

class MenuCardAdapter(private val context: HomeActivity,
                      private val cardMenuList: List<HomeMenuCard>,
                      private val onCardMenuClicked: OnCardMenuClicked)
    : RecyclerView.Adapter<MenuCardAdapter.ViewHolder>() {

    interface OnCardMenuClicked{
        fun onClickedCardMenu(cardName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pizza_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCard = cardMenuList[position]

        Glide.with(context).load(Uri.parse(currentCard.urlPic)).into(holder.cardImageItem)
        holder.cardNameItem.text = currentCard.name

        when (currentCard.name) {
            "Premium" -> {
                holder.cardNameItem.setTextColor(context.getColor(R.color.colorGreen))
                holder.cardNameItem.background = context.getDrawable(R.drawable.underline_small_green)
            }
            "Blanche" -> {
                holder.cardNameItem.setTextColor(context.getColor(R.color.colorBlanche))
                holder.cardNameItem.background = context.getDrawable(R.drawable.underline_small_yellow)
            }
            "Tomate" -> {
                holder.cardNameItem.setTextColor(context.getColor(R.color.colorTomate))
                holder.cardNameItem.background = context.getDrawable(R.drawable.underline_small_red)
            }
            "Dessert" -> {
                holder.cardNameItem.setTextColor(context.getColor(R.color.colorWhite))
                holder.cardNameItem.background = context.getDrawable(R.drawable.underline_small_white)
            }
        }

        holder.itemView.setOnClickListener { onCardMenuClicked.onClickedCardMenu(currentCard.name) }

    }

    override fun getItemCount(): Int = cardMenuList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cardNameItem : TextView = view.findViewById(R.id.pizza_item_name)
        val cardImageItem : ImageView = view.findViewById(R.id.pizza_item_image)
    }

}