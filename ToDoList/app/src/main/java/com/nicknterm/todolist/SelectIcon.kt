package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_select_icon.*

class SelectIcon : AppCompatActivity() {
    private var item: FragmentRecycleViewItems? = null
    private var mode: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        when (sharedPref.getString("color", "primaryCyan")) {
            "red" -> setTheme(R.style.MyTheme_red)
            "orange" -> setTheme(R.style.MyTheme_orange)
            "yellow" -> setTheme(R.style.MyTheme_yellow)
            "green" -> setTheme(R.style.MyTheme_green)
            "lime" -> setTheme(R.style.MyTheme_lime)
            "cyan" -> setTheme(R.style.MyTheme_light_cyan)
            "primaryCyan" -> setTheme(R.style.MyTheme_cyan)
            "blue" -> setTheme(R.style.MyTheme_blue)
            "purple" -> setTheme(R.style.MyTheme_purple)
            else -> setTheme(R.style.MyTheme_cyan)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_icon)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        mode = intent.getStringExtra("Mode")
        item = intent.getParcelableExtra("ItemToAdd")
    }
    fun iconClicked(view: View){
        when(resources.getResourceEntryName(view.id)){
            "BedTimeIconButton" -> item!!.setIcon("ic_baseline_bedtime")
            "BiologyIconButton" -> item!!.setIcon("ic_baseline_biology")
            "BookIconButton" -> item!!.setIcon("ic_baseline_book")
            "BreakfastIconButton" -> item!!.setIcon("ic_baseline_breakfast")
            "ClockIconButton" -> item!!.setIcon("ic_baseline_clock")
            "ComputerIconButton" -> item!!.setIcon("ic_baseline_computer")
            "CoronavirusIconButton" -> item!!.setIcon("ic_baseline_coronavirus")
            "FamilyTimeIconButton" -> item!!.setIcon("ic_baseline_family_time")
            "FingerprintIconButton" -> item!!.setIcon("ic_baseline_fingerprint")
            "FoodIconButton" -> item!!.setIcon("ic_baseline_food")
            "MathIconButton" -> item!!.setIcon("ic_baseline_math")
            "MusicIconButton" -> item!!.setIcon("ic_baseline_music")
            "NatureIconButton" -> item!!.setIcon("ic_baseline_nature")
            "ScienceIconButton" -> item!!.setIcon("ic_baseline_science")
            "SportsIconButton" -> item!!.setIcon("ic_baseline_sports")
            "TypingIconButton" -> item!!.setIcon("ic_baseline_typing")
            "VideoGamesIconButton" -> item!!.setIcon("ic_baseline_videogames")
            "WarningIconButton" -> item!!.setIcon("ic_baseline_warning")
            "WorkIconButton" -> item!!.setIcon("ic_baseline_work")
        }
        val intent = Intent(this, AddTasksInRecycleView::class.java)
        intent.putExtra("Day", item?.getDay())
        intent.putExtra("ItemToAdd", item)
        intent.putExtra("Mode", mode)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        val intent = Intent(this, AddTasksInRecycleView::class.java)
        intent.putExtra("Day", item?.getDay())
        intent.putExtra("ItemToAdd", item)
        intent.putExtra("Mode", mode)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}