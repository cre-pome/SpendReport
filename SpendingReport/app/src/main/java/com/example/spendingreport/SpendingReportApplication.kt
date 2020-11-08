package com.example.spendingreport

import android.app.Application
import io.realm.Realm

class SpendingReportApplication :Application() {
    override fun onCreate(){
        super.onCreate()
        Realm.init(this)
    }

}