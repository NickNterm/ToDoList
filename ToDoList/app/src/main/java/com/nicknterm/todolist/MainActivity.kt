package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recycle_view.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var tabAdapter: TabAdapter? = null
    private var dayToStart: Int = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        if(sharedPref.getBoolean("theme", false)){
            when (sharedPref.getString("color", "primaryCyan")) {
                "red" -> setTheme(R.style.MyTheme_red)
                "orange" -> setTheme(R.style.MyTheme_orange)
                "yellow" -> setTheme(R.style.MyTheme_yellow)
                "green" -> setTheme(R.style.MyTheme_green)
                "lime" -> setTheme(R.style.MyTheme_lime)
                "cyan" -> setTheme(R.style.MyTheme_light_cyan)
                "primaryCyan" -> setTheme(R.style.MyTheme_cyan)
                "blue" -> setTheme(R.style.MyTheme_blue)
            }
        }else{
            when (sharedPref.getString("color", "primaryCyan")) {
                "red" -> setTheme(R.style.MyTheme_red_day)
                "orange" -> setTheme(R.style.MyTheme_orange_day)
                "yellow" -> setTheme(R.style.MyTheme_yellow_day)
                "green" -> setTheme(R.style.MyTheme_green_day)
                "lime" -> setTheme(R.style.MyTheme_lime_day)
                "cyan" -> setTheme(R.style.MyTheme_light_cyan_day)
                "primaryCyan" -> setTheme(R.style.MyTheme_cyan_day)
                "blue" -> setTheme(R.style.MyTheme_blue_day)
            }
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dayToStart = if (intent.getStringExtra("Day") != null) {
            dayToInt(intent.getStringExtra("Day")!!)
        } else {
            val sdf = SimpleDateFormat("EEEE")
            dayToInt(sdf.format(Date()))
        }
        setSupportActionBar(MainActivityToolBar)
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Monday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Tuesday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Wednesday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Thursday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Friday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Saturday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Sunday"))
        MainActivityTabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val tabAdapter = TabAdapter(this, supportFragmentManager, MainActivityTabLayout.tabCount)
        MainActivityViewPager.adapter = tabAdapter
        MainActivityViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(MainActivityTabLayout))
        MainActivityViewPager.currentItem = dayToStart
        MainActivityTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                MainActivityViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        AddButton.setOnClickListener {
            val intent: Intent = Intent(this, AddTasksInRecycleView::class.java)
            intent.putExtra("Day", intToDay(MainActivityViewPager.currentItem))
            startActivity(intent)
        }
        SettingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }
    }

    private fun dayToInt(day: String): Int {
        return when (day) {
            "Monday" -> 0
            "Tuesday" -> 1
            "Wednesday" -> 2
            "Thursday" -> 3
            "Friday" -> 4
            "Saturday" -> 5
            "Sunday" -> 6
            else -> 0
        }
    }

    private fun intToDay(int: Int): String {
        return when (int) {
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            6 -> "Sunday"
            else -> ""
        }
    }
}