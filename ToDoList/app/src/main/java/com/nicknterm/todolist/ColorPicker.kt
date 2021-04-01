package com.nicknterm.todolist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import kotlinx.android.synthetic.main.activity_color_picker.*

class ColorPicker : AppCompatActivity() {
    var finalColor: Int = R.color.red
    var item: FragmentRecycleViewItems? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_picker)
        item = intent.getParcelableExtra("ItemToAdd")
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

    }

    override fun onBackPressed() {
        goToAddTask()
    }
    private fun goToAddTask(){
        val intent = Intent(this, AddTasksInRecycleView::class.java)
        item?.setColor(finalColor)
        intent.putExtra("Day", item?.getDay())
        intent.putExtra("ItemToAdd", item)
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