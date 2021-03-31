package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_add_tasks_in_recycle_view.*
import kotlinx.android.synthetic.main.time_picker_dialog.*
import java.util.*

class AddTasksInRecycleView : AppCompatActivity() {
    private var day: String? = null
    private var dbHandler: DatabaseHandler? = null
    private var startTime: String? = null
    private var endTime: String? = null
    private var color: Int? = null
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tasks_in_recycle_view)
        setSupportActionBar(AddTaskToolBar)
        day = intent.getStringExtra("Day")
        color = intent.getIntExtra("Color",0)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Add Task In $day"
        dbHandler = DatabaseHandler(this)
        AddTask.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            endTime = EndTimeTextView.text.toString()
            startTime = StartTimeTextView.text.toString()
            val item = endTime?.let { it1 -> startTime?.let { it2 -> FragmentRecycleViewItems(0, NameEditText.text.toString(), it2, it1, day!!) } }
            if (item != null) {
                dbHandler!!.addTaskInDay(item)
            }
            intent.putExtra("Day", day)
            startActivity(intent)
        }
        StartTimeButton.setOnClickListener {
            showTimerPickerDialog(StartTimeTextView)
        }
        PickColorButton.setOnClickListener {
            val intent = Intent(this, ColorPicker::class.java)
            intent.putExtra("Day", day)
            startActivity(intent)
            finish()
        }
        if (color != 0) {
            ColorText.setTextColor(R.color.black)
            llColorCardView.setBackgroundResource(color!!)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun showTimerPickerDialog(text: TextView){
        var result = String.format("%02d:%02d",Calendar.getInstance().time.hours, Calendar.getInstance().time.minutes)
        val timePickerDialog = Dialog(this)
        timePickerDialog.setContentView(R.layout.time_picker_dialog)
        timePickerDialog.TimePickerInDialog.setIs24HourView(true)
        timePickerDialog.TimePickerInDialog.setOnTimeChangedListener {view, hourOfDay, minute ->  result=String.format("%02d:%02d",hourOfDay,minute)}
        timePickerDialog.TimePickerDialogCancelButton.setOnClickListener {
            timePickerDialog.dismiss()
        }
        timePickerDialog.TimePickerDialogSelectButton.setOnClickListener {
            text.text =  result
            timePickerDialog.dismiss()
        }
        timePickerDialog.show()
    }
}