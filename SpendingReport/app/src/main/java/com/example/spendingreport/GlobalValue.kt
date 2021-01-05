package com.example.spendingreport

import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GlobalValue : Application(){
    @RequiresApi(Build.VERSION_CODES.O)
    var yearOfMonth: String? = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

    // 今月の基準日
    @RequiresApi(Build.VERSION_CODES.O)
    val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    val baseDay: Date = df.parse("$yearOfMonth-25")


    companion object {
        private var instance :GlobalValue? = null

        fun  getInstance(): GlobalValue {
            if (instance == null)
                instance = GlobalValue()

            return instance!!
        }
    }
}