package com.example.spendingreport

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.one_history.view.*

class ViewHolder(one_history: View) :RecyclerView.ViewHolder(one_history){
    var valueText : TextView? = null
    var kindText : TextView? = null
    var dateText : TextView? = null

    init{

        //　ビューホルダーのプロパティとレイアウトのViewの対応
        valueText = one_history.spendValue
        kindText = one_history.spendKind
        dateText = one_history.spendDate

    }
}