package com.nicknterm.todolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity() {

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
        setContentView(R.layout.settings_activity)
        setSupportActionBar(SettingsToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.settings, SettingsFragment())
                    .commit()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        return super.onSupportNavigateUp()
    }
}