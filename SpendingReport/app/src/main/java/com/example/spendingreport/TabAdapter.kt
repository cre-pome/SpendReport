package com.example.spendingreport

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager, private val context: Context): FragmentPagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> { return TabThisMonthFragment() }
            else ->  { return TabSpendHistoryFragment() }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> { return context.getString(R.string.tab_this_month_report) }
            else ->  { return context.getString(R.string.tab_spend_history) }
        }
    }

    override fun getCount(): Int {
        return 2
    }
}