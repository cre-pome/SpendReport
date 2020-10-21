package com.example.spendingreport

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.get
import com.example.spendingreport.db.DatabaseManager
import com.example.spendingreport.ui.main.TimePickerFragment
import kotlinx.android.synthetic.main.activity_spend_add.*

class SpendAddActivity : AppCompatActivity() , TimePickerFragment.OnTimeSelectedListener{
    private val spinnerFirstIndex = 5
    private val dbm = DatabaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

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
    fun onInsertButton(){
        // 出費額
        val spend = editTextNumber.text
        
    }

    // 日付入力欄
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() {view, year, month, dayOfMonth->
                dateSetText.text = "${year}/${month + 1}/${dayOfMonth}"
            },
            2020,
            3,
            1)
        datePickerDialog.show()
    }

    override fun onSelected(hourOfDay: Int, minute: Int) {
        //　時刻入力テキスト
        val timeSetText: TextView = findViewById(R.id.timeSetText)

       timeSetText.text="%1$02d:%2$02d" .format(hourOfDay, minute)
    }
}