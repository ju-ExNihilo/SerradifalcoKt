package com.jgdeveloppement.pizza_serradifalco.validateOrder

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class DatePickerFragment(private val activity: ValidateOrderActivity,
                         private val editText: TextInputEditText)
    : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val c = Calendar.getInstance()
    private val year = c.get(Calendar.YEAR)
    private val month = c.get(Calendar.MONTH)
    private val day = c.get(Calendar.DAY_OF_MONTH)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val datePicker = DatePickerDialog(activity, this, year, month, day)
        datePicker.datePicker.minDate = c.timeInMillis
        return datePicker
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val finalMonth = if(month < 10) "0$month" else "$month"
        editText.setText("$day/$finalMonth/$year")
    }
}
