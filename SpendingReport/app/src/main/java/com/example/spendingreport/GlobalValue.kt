package com.example.spendingreport

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GlobalValue : Application(){
    @RequiresApi(Build.VERSION_CODES.O)
    var yearOfMonth: String? = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

    companion object {
        private var instance :GlobalValue? = null

        fun  getInstance(): GlobalValue {
            if (instance == null)
                instance = GlobalValue()

            return instance!!
        }
    }
}