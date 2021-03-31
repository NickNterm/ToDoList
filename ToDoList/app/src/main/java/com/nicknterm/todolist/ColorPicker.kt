package com.nicknterm.todolist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.activity_color_picker.*

class ColorPicker : AppCompatActivity() {
    var finalColorString: Int? = null
    var day: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)
        day = intent.getStringExtra("Day")
        ColorPickerRed.setOnClickListener {
            deselectAll()
            ColorPickerRed.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColorString = R.color.red
        }
        ColorPickerBlue.setOnClickListener {
            deselectAll()
            ColorPickerBlue.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColorString = R.color.blue
        }
        ColorPickerCyan.setOnClickListener {
            deselectAll()
            ColorPickerCyan.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColorString = R.color.cyan
        }
        ColorPickerGreen.setOnClickListener {
            deselectAll()
            ColorPickerGreen.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColorString = R.color.green
        }
        ColorPickerLime.setOnClickListener {
            deselectAll()
            ColorPickerLime.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColorString = R.color.lime
        }
        ColorPickerOrange.setOnClickListener {
            deselectAll()
            ColorPickerOrange.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColorString = R.color.orange
        }
        ColorPickerYellow.setOnClickListener {
            deselectAll()
            ColorPickerYellow.setBackgroundResource(R.drawable.color_selected_in_color_picker)
            finalColorString = R.color.yellow
        }
        ColorPickerSelect.setOnClickListener {
            val intent = Intent(this, AddTasksInRecycleView::class.java)
            intent.putExtra("Color", finalColorString)
            intent.putExtra("Day", day)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, AddTasksInRecycleView::class.java)
        intent.putExtra("Color", finalColorString)
        intent.putExtra("Day", day)
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