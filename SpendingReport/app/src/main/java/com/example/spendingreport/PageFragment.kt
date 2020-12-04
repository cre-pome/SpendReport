package com.example.spendingreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
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

        thisMonth.text = globalValue.yearOfMonth

        // realmインスタンス
        realm = Realm.getDefaultInstance()
        val realmResults = realm.where(SpendHistory::class.java)
            .findAll()
            .sort("id", Sort.DESCENDING)

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