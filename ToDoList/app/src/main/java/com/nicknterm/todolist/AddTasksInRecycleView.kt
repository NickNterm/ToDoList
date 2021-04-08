package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.sax.EndTextElementListener
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_tasks_in_recycle_view.*
import kotlinx.android.synthetic.main.time_picker_dialog.*
import java.util.*

class AddTasksInRecycleView : AppCompatActivity() {
    private var day: String? = null
    private var dbHandler: DatabaseHandler? = null
    private var startTime: String? = null
    private var endTime: String? = null
    private var color: Int? = null
    private var name: String? = null
    private var itemToAdd: FragmentRecycleViewItems? = null
    private var mode: String? = null
    private var elementId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tasks_in_recycle_view)
        setSupportActionBar(AddTaskToolBar)
        mode = intent.getStringExtra("Mode")
        day = intent.getStringExtra("Day")
        name = intent.getStringExtra("Name")
        itemToAdd = intent.getParcelableExtra("ItemToAdd")
        name = itemToAdd?.getName()
        endTime = itemToAdd?.getTimeEnd()
        startTime = itemToAdd?.getTimeStart()
        color = itemToAdd?.getColor()
        refreshUI()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Add Task In $day"
        dbHandler = DatabaseHandler(this)
        elementId = day?.let { dbHandler!!.getElementId(it) }
        elementId = elementId?.plus(1)
        AddTask.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            endTime = EndTimeTextView.text.toString()
            startTime = StartTimeTextView.text.toString()
            name = NameEditText.text.toString()
            if (name!!.isEmpty()){
                Snackbar.make(it,"Enter a task name", Snackbar.LENGTH_LONG)
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                        .show()
            }else if(color==null) {
                Snackbar.make(it,"Select a color", Snackbar.LENGTH_LONG)
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                        .show()
            }else{
                val item = endTime?.let { it1 -> startTime?.let { it2 -> FragmentRecycleViewItems(elementId!!, name, it2, it1, day!!, color!!) } }
                if (item != null) {
                    dbHandler!!.addTaskInDay(item)
                }
                intent.putExtra("Day", day)
                startActivity(intent)
            }
        }

        StartTimeButton.setOnClickListener {
            showTimerPickerDialog(StartTimeTextView)
        }

        EndTimeButton.setOnClickListener{
            showTimerPickerDialog(EndTimeTextView)
        }

        PickColorButton.setOnClickListener {
            val intent = Intent(this, ColorPicker::class.java)
            val item = if (color!=null) {
                FragmentRecycleViewItems(elementId!!, NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, color!!)
            } else{
                FragmentRecycleViewItems(elementId!!, NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, 1)
            }
            intent.putExtra("ItemToAdd", item)
            intent.putExtra("Mode", mode)
            startActivity(intent)
            finish()
        }
        EditButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            endTime = EndTimeTextView.text.toString()
            startTime = StartTimeTextView.text.toString()
            name = NameEditText.text.toString()
            if (name!!.isEmpty()){
                Snackbar.make(it,"Enter a task name", Snackbar.LENGTH_LONG)
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                        .show()
            }else if(color==null) {
                Snackbar.make(it,"Select a color", Snackbar.LENGTH_LONG)
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                        .show()
            }else{
                val item = endTime?.let { it1 -> startTime?.let { it2 -> FragmentRecycleViewItems(0, name, it2, it1, day!!, color!!) } }
                if (item != null) {
                    day?.let { it1 -> itemToAdd?.let { it2 -> dbHandler!!.updateItem(it1, it2.getId(),item) } }
                }
                intent.putExtra("Day", day)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun refreshUI(){
        if(startTime == null) {
            startTime = String.format("%02d:%02d",Calendar.getInstance().time.hours, Calendar.getInstance().time.minutes)
        }
        StartTimeTextView.text = startTime
        if(endTime == null) {
            endTime = String.format("%02d:%02d",(Calendar.getInstance().time.hours + 1) % 24, Calendar.getInstance().time.minutes)
        }
        EndTimeTextView.text = endTime
        if(name != null){
            NameEditText.setText(name)
        }
        if (color != null) {
            llColorCardView.setBackgroundResource(color!!)
        }
        if (mode == "Edit"){
            DeleteCardView.visibility = View.VISIBLE
            EditCardView.visibility = View.VISIBLE
            SaveCardView.visibility = View.GONE
        }else{
            DeleteCardView.visibility = View.GONE
            EditCardView.visibility = View.GONE
            SaveCardView.visibility = View.VISIBLE
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