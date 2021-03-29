package com.jgdeveloppement.pizza_serradifalco.utils

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

object Utils {
     const val RC_SIGN_IN = 123
     const val NONE = "none"
     const val NONE_FR = "Aucun"
     const val POST_CODE_83160 = "83160"
     const val POST_CODE_83000 = "83000"
     const val POST_CODE_83100 = "83100"
     const val POST_CODE_83130 = "83130"
     const val PRODUCT_ID = "productId"
     const val DRINK = "Drink"
     const val EXTRA = "Extra"
     const val DESSERT = "Dessert"
     const val DESSERT_CATEGORY = "DESSERT"
     const val BLANCHE_CATEGORY = "BLANCHE"
     const val PREMIUM_CATEGORY = "PREMIUM"
     const val TOMATE_CATEGORY = "TOMATE"
     const val LARGE = "Large"
     const val MIDDLE = "Moyenne"
     const val CARD_NAME = "cardName"

     fun showSnackBar(view: View?, message: String) {
          Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).show()
     }

     fun setVisibilityError(editText: String, textViewError: TextView){
          if (editText.isBlank()) textViewError.visibility = View.VISIBLE else textViewError.visibility = View.GONE
     }
}