package com.example.citamedica.util
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TimerPickerHelper : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var listener: TimePickerDialog.OnTimeSetListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity as Context, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        listener?.onTimeSet(view, hourOfDay, minute)
    }

    companion object {
        fun newInstance(listener: TimePickerDialog.OnTimeSetListener): TimerPickerHelper {
            val fragment = TimerPickerHelper()
            fragment.listener = listener
            return fragment
        }
    }
}

