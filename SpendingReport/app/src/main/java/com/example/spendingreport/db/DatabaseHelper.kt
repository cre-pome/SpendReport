package com.example.spendingreport.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.StringBuilder

class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
            // データベースファイル名の定数
            private const val DATABASE_NAME = "spend.db"

            // バージョン情報の定数フィールド
            private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase){
        // テーブル作成用DQL文字列の作成
        val sb = StringBuilder()
        sb.append("CREATE TABLE spend_history(")
        sb.append("id INTEGER PRIMARY KEY,")
        sb.append("name TEXT,")
        sb.append("kind TEXT,")
        sb.append("spend money,")
        sb.append("SpendDate datetime")
        sb.append(");")
        var sql = sb.toString()

        db.execSQL(sql)
    }


    override fun onUpgrade(db:SQLiteDatabase, oldVersion: Int, newVersion: Int){}


}