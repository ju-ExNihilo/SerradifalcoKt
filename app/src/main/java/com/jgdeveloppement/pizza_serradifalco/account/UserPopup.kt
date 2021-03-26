package com.jgdeveloppement.pizza_serradifalco.account

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.textfield.TextInputEditText
import com.jgdeveloppement.pizza_serradifalco.R
import com.jgdeveloppement.pizza_serradifalco.utils.UserData

class UserPopup(context: Context?, private val onClickValidateButton: OnClickValidateButton) : Dialog(context!!) {

    interface OnClickValidateButton{
        fun onClickedValidateButton(firstName: String, lastName: String, phone: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_update_user)
        setupComponents()
    }

    private fun setupComponents() {
        val firstName = findViewById<TextInputEditText>(R.id.popup_firstname_edit_text)
        val lastName = findViewById<TextInputEditText>(R.id.popup_lastname_edit_text)
        val phone = findViewById<TextInputEditText>(R.id.popup_phone_edit_text)
        val validateButton = findViewById<Button>(R.id.popup_validate_button)
        val closeButton = findViewById<ImageButton>(R.id.popup_close_button)

        firstName.setText(UserData.userFirstName)
        lastName.setText(UserData.userLastName)
        phone.setText(UserData.userPhone)

        closeButton.setOnClickListener { dismiss() }
        validateButton.setOnClickListener { onClickValidateButton.onClickedValidateButton(firstName.text.toString(), lastName.text.toString(), phone.text.toString());dismiss() }
    }
}