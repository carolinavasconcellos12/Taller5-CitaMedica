package com.example.citamedica

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerHelper : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var listener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        // Create a new instance of DatePickerDialog and return it
        val picker= DatePickerDialog(activity as Context, this, year, month, day)
        picker.datePicker.minDate= c.timeInMillis
        return picker
    }

    override fun onDateSet(view: DatePicker, day: Int, month: Int, year: Int) {
        listener?.onDateSet(view, day, month, year)
    }

    //Pasar el listener como parametro al DialogFragment
    companion object {
        fun newInstance(listener: DatePickerDialog.OnDateSetListener): DatePickerHelper {
            val fragment = DatePickerHelper()
            fragment.listener = listener
            return fragment
        }

    }
}



