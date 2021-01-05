package com.example.spendingreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.Sort
import kotlinx.android.synthetic.main.tab_fragment_thismonth.*
import java.text.SimpleDateFormat
import java.util.*

class TabThisMonthFragment: Fragment(){
    private lateinit var  adapter: CustomRecyclerViewAdapter
    private lateinit var  layoutManager:RecyclerView.LayoutManager
    private lateinit var realm: Realm


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_fragment_thismonth,container,false)
    }

    override fun onStart() {
        super.onStart()

        //　グローバル変数
        val globalValue = GlobalValue.getInstance()

        //　今の月を表示
//        thisMonth.text = globalValue.yearOfMonth


        // realmインスタンス
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        realm = Realm.getInstance(realmConfiguration)


        val realmResults :RealmResults<SpendHistory> ;
        val df = SimpleDateFormat("yyyy-MM-dd")


        if (Date() >= globalValue.baseDay) {
            val cal = Calendar.getInstance()
            cal.time = globalValue.baseDay
            cal.add(Calendar.MONTH, 1)

            // 今月の基準日から来月の基準日前までの出費履歴を取得
            realmResults = realm.where(SpendHistory::class.java)
                .greaterThanOrEqualTo("spendDate", globalValue.baseDay)
                .lessThan("spendDate", cal.time)
                .findAll()
                .sort("spendDate", Sort.DESCENDING)


            cal.add(Calendar.DATE, -1)
            thisMonth.text = "${df.format(globalValue.baseDay)}" + " ~ " + "${df.format(cal.time)}"
        } else {
            val cal = Calendar.getInstance()
            cal.time = globalValue.baseDay
            cal.add(Calendar.MONTH, -1)

            // 前月の基準日から今月の基準日前までの出費履歴を取得
            realmResults = realm.where(SpendHistory::class.java)
                .greaterThanOrEqualTo("spendDate", cal.time)
                .lessThan("spendDate", globalValue.baseDay)
                .findAll()
                .sort("spendDate", Sort.DESCENDING)

            val calEnd = Calendar.getInstance()
            calEnd.time = globalValue.baseDay
            calEnd.add(Calendar.DATE, -1)

            thisMonth.text = "${df.format(cal.time)}" + " ~ " + "${df.format(calEnd.time)}"
        }


        //　金額の合計
        val sum = realmResults.sum("spend")

        sumValue.text = "総額 " + sum.toString() + "円"

        layoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = layoutManager

        adapter = CustomRecyclerViewAdapter(realmResults)
        recyclerView.adapter = this.adapter
    }
}

class TabSpendHistoryFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tab_fragment_spendhistory,container,false)
    }
}