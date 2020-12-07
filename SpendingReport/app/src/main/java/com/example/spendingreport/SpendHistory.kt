package com.example.spendingreport

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class SpendHistory:RealmObject() {

    @PrimaryKey
    var id:Long = 0
    var spend:Long = 0
    var kind:String = ""
    var spendDate:Date = Date()
    var spendMonth:String = ""
}