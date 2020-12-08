package com.example.spendingreport

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.android.synthetic.main.tab_fragment_thismonth.*


class MainActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val realmConfiguration = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()
        realm = Realm.getInstance(realmConfiguration)

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

        viewPager.adapter = TabAdapter(supportFragmentManager,this)
        tabs.setupWithViewPager(viewPager)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    //　出費登録ボタンが押された時の関数
    fun onSpendAddButtonClick(view: View){
        insertButton.isEnabled = false
        insertButton.postDelayed({
            insertButton.isEnabled = true
        }, 500L)

        val intent = Intent(this, SpendAddActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}