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
import io.realm.RealmConfiguration
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_spend_add.*
import kotlinx.android.synthetic.main.tab_fragment_thismonth.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class SpendAddActivity : AppCompatActivity() , TimePickerFragment.OnTimeSelectedListener{
    private val spinnerFirstIndex = 5

    private lateinit var realm: Realm

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spend_add)

        // スピナーを定義
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

        //　選択された履歴ID
        val historyId = intent.getLongExtra("id", 0L)
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        realm = Realm.getInstance(realmConfiguration)

        if (historyId > 0L) {
            val historyPress = realm.where<SpendHistory>().equalTo("id", historyId ).findFirst()

            // 出費額を表示
            editTextNumber.setText(historyPress?.spend.toString())

            // 出費種別を表示
            spinner.setSelection( resources.getStringArray(R.array.kinds_array).indexOf(historyPress?.kind))

            // 出費日時(年月)を表示
            dateSetText.text = SimpleDateFormat("yyyy-MM-dd").format(historyPress?.spendDate)
            println("ccc:"+SimpleDateFormat("yyyy-MM-dd").format(historyPress?.spendDate))

            // 出費日時(時刻)を表示
            val format = SimpleDateFormat("HH:mm")
            timeSetText.text = format.format(historyPress?.spendDate)

        } else {
            deleteButton.visibility = View.INVISIBLE

            spinner.setSelection(spinnerFirstIndex)



            //　日付入力テキストに現在日をセット
            dateSetText.text = LocalDate.now().toString()


            timeSetText.text = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

        }

        //　日付入力テキスト
        dateSetText.setOnClickListener{
            showDatePicker()
        }


        //　時刻入力テキスト
        timeSetText.setOnClickListener{
            TimePickerFragment().show(supportFragmentManager, "timePicker")
        }


        // 登録ボタンを設定　
        saveButton.setOnClickListener {
            saveButton.isEnabled = false
            saveButton.postDelayed({
                saveButton.isEnabled = true
            }, 500L)

            // 出費額
            val spend = editTextNumber.text.toString().toLong()

            // 出費種別
            val kind = spinner.selectedItem

            // 日付と時刻を文字列に変換
            val dateStr = dateSetText.text.toString() + "T" + timeSetText.text + ":00"

            // 日付のフォーマット
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

            // 文字列をDate型に変換
            val dt = df.parse(dateStr)

            //　日付をLocalDateに変換
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-d'T'HH:mm:ss")
            val date: LocalDate = LocalDate.parse( dateStr, formatter)

            // 日付の月のみ取得
            val dm = date.year.toString() + "-" + date.monthValue.toString()

            when(historyId) {
                0L -> {
                    realm.executeTransaction{

                        val maxid = realm.where<SpendHistory>().max("id")
                        val nextId = (maxid?.toLong() ?: 0L) + 1L
                        val spendHistory = realm.createObject<SpendHistory>(nextId)

                        spendHistory.kind = kind as String
                        spendHistory.spend = spend
                        spendHistory.spendDate = dt
                        spendHistory.spendMonth = dm
                    }

                    Toast.makeText(applicationContext, "保存しました", Toast.LENGTH_SHORT).show()
                    finish()
                }

                else -> {
                    realm.executeTransaction{

                        val spendHistory = realm.where<SpendHistory>()
                            .equalTo("id", historyId)
                            ?.findFirst()

                        spendHistory?.kind = kind as String
                        spendHistory?.spend = spend
                        spendHistory?.spendDate = dt
                        spendHistory?.spendMonth = dm
                    }

                    Toast.makeText(applicationContext, "更新しました", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

        }

        // 履歴を削除
        deleteButton.setOnClickListener{

            deleteButton.isEnabled = false
            deleteButton.postDelayed({
                deleteButton.isEnabled = true
            }, 500L)

            realm.executeTransaction {
                val spendHistory = realm.where<SpendHistory>()
                    .equalTo("id", historyId)
                    ?.findFirst()
                    ?.deleteFromRealm()
            }

            Toast.makeText(applicationContext, "削除しました", Toast.LENGTH_SHORT).show()
            finish()
        }


    }

    // 戻るボタン
    fun onButtonTapped(view: View?){
        finish()
    }

    // 日付入力欄
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {

        val s = LocalDate.now()


        println("month:"+s.monthValue)
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener() {view, year, month, dayOfMonth->
                dateSetText.text = "${year}-${month + 1}-${dayOfMonth}"
            },
            s.year,
            s.monthValue - 1 ,
            s.dayOfMonth)
        datePickerDialog.show()
    }

    override fun onSelected(hourOfDay: Int, minute: Int) {
        //　時刻入力テキスト
        val timeSetText: TextView = findViewById(R.id.timeSetText)

       timeSetText.text="%1$02d:%2$02d" .format(hourOfDay, minute)
    }

}