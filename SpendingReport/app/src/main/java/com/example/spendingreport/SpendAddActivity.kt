package com.example.spendingreport

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.spendingreport.ui.main.TimePickerFragment
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_spend_add.*
import java.time.LocalDate
import java.util.*

class SpendAddActivity : AppCompatActivity() , TimePickerFragment.OnTimeSelectedListener{
    private val spinnerFirstIndex = 5

    private lateinit var realm: Realm

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        realm = Realm.getDefaultInstance()

        setContentView(R.layout.activity_spend_add)

        //　時刻入力テキスト
        timeSetText.setOnClickListener{
            TimePickerFragment().show(supportFragmentManager, "timePicker")
        }

        //　日付入力テキスト
        dateSetText.setOnClickListener{
            showDatePicker()
        }

        // スピナーに値を入れる
        val spinner: Spinner = findViewById(R.id.spinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.kinds_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.setSelection(spinnerFirstIndex)
    }

    // 戻るボタン
    fun onButtonTapped(view: View?){
        finish()
    }

    // 登録ボタン押下
    fun onInsertButton(view: View){
        // 出費額
        val spend = editTextNumber.text.toString().toLong()

        // 出費種別
        val kind = spinner.selectedItem

        realm.executeTransaction{

            val maxid = realm.where<SpendHistory>().max("id")
            val nextId = (maxid?.toLong() ?: 0L) + 1L
            val spendHistory = realm.createObject<SpendHistory>(nextId)

            spendHistory.kind = kind as String
            spendHistory.spend = spend
            spendHistory.spendDate = Date()
        }

        Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
        finish()
        
    }

    // 日付入力欄
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {

        val s = LocalDate.now()


        println(s)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() {view, year, month, dayOfMonth->
                dateSetText.text = "${year}/${month}/${dayOfMonth}"
            },
            s.year,
            s.monthValue ,
            s.dayOfMonth)
        datePickerDialog.show()
    }

    override fun onSelected(hourOfDay: Int, minute: Int) {
        //　時刻入力テキスト
        val timeSetText: TextView = findViewById(R.id.timeSetText)

       timeSetText.text="%1$02d:%2$02d" .format(hourOfDay, minute)
    }
}