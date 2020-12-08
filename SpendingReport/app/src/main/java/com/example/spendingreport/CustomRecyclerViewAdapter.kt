package com.example.spendingreport

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmResults
import java.text.SimpleDateFormat
import java.util.*


class CustomRecyclerViewAdapter(realmResults: RealmResults<SpendHistory>):RecyclerView.Adapter<ViewHolder>() {

    private val rResult :RealmResults<SpendHistory> = realmResults

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

        val format = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val timeZoneJP: TimeZone = TimeZone.getTimeZone("Asia/Tokyo")
        format.timeZone = timeZoneJP
        holder.dateText?.text = format.format(spendhistory?.spendDate)
        holder.itemView.setBackgroundColor(
                if (position %2 == 0) Color.LTGRAY else Color.WHITE
        )

        holder.itemView.setOnClickListener{
            holder.itemView.isClickable = false
            val intent = Intent(it.context, SpendAddActivity::class.java)
            intent.putExtra("id", spendhistory?.id)
            it.context.startActivity(intent)

        }
    }
}