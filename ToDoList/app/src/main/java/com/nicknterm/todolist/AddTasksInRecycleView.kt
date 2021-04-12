package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.sax.EndTextElementListener
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
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
                if(itemToAdd != null) {
                    FragmentRecycleViewItems(itemToAdd!!.getId(), NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, color!!)
                }else{
                    FragmentRecycleViewItems(0, NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, color!!)
                }
            } else{
                if(itemToAdd != null) {
                    FragmentRecycleViewItems(itemToAdd!!.getId(), NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, 1)
                }else{
                    FragmentRecycleViewItems(0, NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, 1)
                }
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
            color
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
                val item = FragmentRecycleViewItems(itemToAdd!!.getId(), name, startTime!!, endTime!!, day!!, color!!)
                day?.let { it1 -> itemToAdd?.let { it2 -> dbHandler!!.updateItem(it1, it2.getId(),item) } }
                intent.putExtra("Day", day)
                startActivity(intent)
            }
        }
        DeleteButton.setOnClickListener{
            dbHandler!!.deleteItem(day!!, itemToAdd!!.getId())
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Day", day)
            startActivity(intent)
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