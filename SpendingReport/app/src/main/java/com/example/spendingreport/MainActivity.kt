package com.example.spendingreport

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager: ViewPager = findViewById(R.id.view_pager)

        val tabs: TabLayout = findViewById(R.id.tabs)

        // 「今月の出費記録」タブ
        val thisMonthReport = tabs.newTab()
        thisMonthReport.text = getString(R.string.tab_this_month_report)

        // 「出費履歴」タブ
        val spendHistoryTab = tabs.newTab()
        spendHistoryTab.text = getString(R.string.tab_spend_history)

        tabs.addTab(thisMonthReport)
        tabs.addTab(spendHistoryTab)


        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}