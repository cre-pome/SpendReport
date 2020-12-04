package com.example.spendingreport

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import java.lang.String.format

import android.text.format.DateFormat


class CustomRecyclerViewAdapter(realmResults: RealmResults<SpendHistory>):RecyclerView.Adapter<ViewHolder>() {

    private val rResult :RealmResults<SpendHistory> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        println("rResult:$rResult")
      val view = LayoutInflater.from(parent.context).inflate(R.layout.one_history, parent, false)
        val viewholder = ViewHolder(view)
        return viewholder
    }

    override fun getItemCount(): Int {
        return rResult.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spendhistory = rResult[position]
        holder.valueText?.text =  spendhistory?.spend.toString() + "円"
        holder.kindText?.text = spendhistory?.kind
        holder.dateText?.text = DateFormat.format("yyyy-MM-dd hh:mm" ,spendhistory?.spendDate)
    }
}