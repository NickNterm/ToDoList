package com.nicknterm.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import kotlinx.android.synthetic.main.activity_color_picker.*

class ColorPicker : AppCompatActivity() {
    var finalColor: Int = R.color.red
    var item: FragmentRecycleViewItems? = null
    var mode: String? = null
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
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)
        item = intent.getParcelableExtra("ItemToAdd")
        mode = intent.getStringExtra("Mode")
        ColorPickerRed.setOnClickListener {
            deselectAll()
            ColorPickerRed.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColor = R.color.red
        }
        ColorPickerBlue.setOnClickListener {
            deselectAll()
            ColorPickerBlue.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColor = R.color.blue
        }
        ColorPickerCyan.setOnClickListener {
            deselectAll()
            ColorPickerCyan.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColor = R.color.cyan
        }
        ColorPickerGreen.setOnClickListener {
            deselectAll()
            ColorPickerGreen.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColor = R.color.green
        }
        ColorPickerLime.setOnClickListener {
            deselectAll()
            ColorPickerLime.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColor = R.color.lime
        }
        ColorPickerOrange.setOnClickListener {
            deselectAll()
            ColorPickerOrange.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColor = R.color.orange
        }
        ColorPickerYellow.setOnClickListener {
            deselectAll()
            ColorPickerYellow.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColor = R.color.yellow
        }
        ColorPickerSelect.setOnClickListener {
            goToAddTask()
        }
        ColorPickerCustom.setOnClickListener {
            ColorPickerDialog
                    .Builder(this)        				// Pass Activity Instance
                    .setTitle("Pick Theme")           	// Default "Choose Color"
                    .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                    .setDefaultColor("#000000")     // Pass Default Color
                    .setColorListener { color, colorHex ->
                        // Handle Color Selection
                    }
                    .show()
        }

    }

    override fun onBackPressed() {
        goToAddTask()
    }
    private fun goToAddTask(){
        val intent = Intent(this, AddTasksInRecycleView::class.java)
        item?.setColor(finalColor)
        intent.putExtra("Day", item?.getDay())
        intent.putExtra("ItemToAdd", item)
        intent.putExtra("Mode", mode)
        startActivity(intent)
        finish()
    }
    private fun deselectAll(){
        ColorPickerRed.setBackgroundResource(0)
        ColorPickerBlue.setBackgroundResource(0)
        ColorPickerCyan.setBackgroundResource(0)
        ColorPickerGreen.setBackgroundResource(0)
        ColorPickerLime.setBackgroundResource(0)
        ColorPickerOrange.setBackgroundResource(0)
        ColorPickerYellow.setBackgroundResource(0)
    }
}