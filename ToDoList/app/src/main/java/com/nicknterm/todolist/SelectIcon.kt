package com.nicknterm.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager

class SelectIcon : AppCompatActivity() {
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
        setContentView(R.layout.activity_select_icon)
        fun IconClicked(){

        }
    }
}