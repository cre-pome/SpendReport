package com.example.spendingreport.db

import java.util.*

class DatabaseManager {
    public fun insertSpend( kind:String, spend:Int, spendDate:Date,helper: DatabaseHelper){
        // データベース接続オブジェクト
        val db = helper.writableDatabase

        // INSERT文
        val sqlInsert = "INSERT INTO spend_history(kind, money) VALUES (?, ?, ?)"

        val stmt = db.compileStatement(sqlInsert)
        stmt.bindString(1, kind)
        stmt.bindLong(2, spend.toLong())
        stmt.bindString(3, spendDate.toString())

        // インサートSQLを実行
        stmt.executeInsert()
    }

}