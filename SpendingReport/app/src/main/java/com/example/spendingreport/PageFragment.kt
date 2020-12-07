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
import io.realm.Sort
import kotlinx.android.synthetic.main.tab_fragment_thismonth.*

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
        thisMonth.text = globalValue.yearOfMonth

        // realmインスタンス
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        realm = Realm.getInstance(realmConfiguration)

        // 今月の出費履歴を取得
        val realmResults = realm.where(SpendHistory::class.java)
            .equalTo("spendMonth", globalValue.yearOfMonth)
            .findAll()
            .sort("id", Sort.DESCENDING)

        //　金額の合計
        val sum = realmResults.sum("spend")

        sumValue.text = sum.toString() + "円"

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