package com.sebastianpatino.misseriesapp.ui.signup

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment (
    val listener:(day:Int,month:Int,year:Int) -> Unit):DialogFragment(),DatePickerDialog.OnDateSetListener{

    override fun onDateSet(p0: DatePicker?, day: Int, month: Int, year: Int) {
        listener(year,month,day)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year:Int = c.get(Calendar.YEAR)
        val month:Int = c.get(Calendar.MONTH)
        val day:Int = c.get(Calendar.DAY_OF_MONTH)


        val picker = DatePickerDialog(activity as Context, this,day,month,year)
        picker.datePicker.maxDate = c.timeInMillis
        return picker
    }
}