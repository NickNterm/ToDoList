 package com.nicknterm.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var tabAdapter: TabAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(MainActivityToolBar)
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Monday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Tuesday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Wednesday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Thursday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Friday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Saturday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Sunday"))
        MainActivityTabLayout.tabGravity = TabLayout.GRAVITY_FILL
        DatabaseHandler(this)
        val adapter = TabAdapter(this, supportFragmentManager,
            MainActivityTabLayout.tabCount)
        MainActivityViewPager.adapter = adapter
        tabAdapter = TabAdapter(this, supportFragmentManager,7)
        MainActivityViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(MainActivityTabLayout))
        MainActivityTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                MainActivityViewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}