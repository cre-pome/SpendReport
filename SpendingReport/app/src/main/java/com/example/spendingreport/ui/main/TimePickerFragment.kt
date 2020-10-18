package com.example.spendingreport.ui.main

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateFormat.is24HourFormat
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.viewpager.widget.ViewPager
import com.example.spendingreport.R
import java.text.SimpleDateFormat
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    interface OnTimeSelectedListener{
        fun onSelected(hourOfDay: Int, minute: Int)
    }

    private var listener: OnTimeSelectedListener? = null

    override fun onAttach(context: Context){
        super.onAttach(context)
        when(context){
            is OnTimeSelectedListener -> listener = context
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
        val time = getTimeString(hourOfDay,minute)
        listener?.onSelected(hourOfDay,minute)
    }

    private fun getTimeString(hourOfDay: Int, minute: Int): String{
        val calendar = Calendar.getInstance()
        calendar.set(0,0,0,hourOfDay,minute)
        return SimpleDateFormat("HH : mm").format(calendar.time)
    }
}