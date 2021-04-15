package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_tasks_in_recycle_view.*
import kotlinx.android.synthetic.main.main_recycle_view_item.*
import kotlinx.android.synthetic.main.time_picker_dialog.*
import java.util.*

class AddTasksInRecycleView : AppCompatActivity() {
    private var day: String? = null
    private var dbHandler: DatabaseHandler? = null
    private var mainItem: FragmentRecycleViewItems? = FragmentRecycleViewItems(0,null,null,null,null,null,0,null)
    private var mode: String? = null
    private var elementId: Int? = null

    @RequiresApi(Build.VERSION_CODES.M)
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
        setContentView(R.layout.activity_add_tasks_in_recycle_view)
        setSupportActionBar(AddTaskToolBar)
        mode = intent.getStringExtra("Mode")
        day = intent.getStringExtra("Day")
        mainItem = intent.getParcelableExtra("ItemToAdd")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Add Task In $day"
        dbHandler = DatabaseHandler(this)
        elementId = day?.let { dbHandler!!.getElementId(it) }
        elementId = elementId?.plus(1)
        refreshUI()
        AddTask.setOnClickListener {
            if(mainItem != null) {
                val intent = Intent(this, MainActivity::class.java)
                mainItem!!.setTimeEnd(EndTimeTextView.text.toString())
                mainItem!!.setTimeStart(StartTimeTextView.text.toString())
                mainItem!!.setName(NameEditText.text.toString())
                if (mainItem!!.getName()!!.isEmpty()) {
                    Snackbar.make(it, "Enter a task name", Snackbar.LENGTH_LONG)
                            .setTextColor(resources.getColor(R.color.white))
                            .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                            .show()
                } else if (mainItem?.getColor() == null) {
                    Snackbar.make(it, "Select a color", Snackbar.LENGTH_LONG)
                            .setTextColor(resources.getColor(R.color.white))
                            .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                            .show()
                }else if(mainItem!!.getIcon()==null) {
                    Snackbar.make(it,"Select a Icon", Snackbar.LENGTH_LONG)
                            .setTextColor(resources.getColor(R.color.white))
                            .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                            .show()
                } else {
                    val item = FragmentRecycleViewItems(elementId!!, mainItem?.getName(), mainItem?.getTimeStart(), mainItem?.getTimeEnd(), day!!, mainItem?.getIcon(), mainItem?.getNotify()!!, mainItem?.getColor())
                    dbHandler!!.addTaskInDay(item)
                    intent.putExtra("Day", day)
                    startActivity(intent)
                    finish()
                }
            }else{
                Snackbar.make(it, "Cannot be empty", Snackbar.LENGTH_LONG)
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                        .show()
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
            val item = if (mainItem?.getColor() !=null) {
                if(mainItem != null) {
                    FragmentRecycleViewItems(mainItem!!.getId(), NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, mainItem?.getIcon(),mainItem?.getNotify()!!, mainItem?.getColor())
                }else{
                    FragmentRecycleViewItems(0, NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, mainItem?.getIcon(), 0, mainItem?.getColor())
                }
            } else{
                if(mainItem != null) {
                    FragmentRecycleViewItems(mainItem!!.getId(), NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, mainItem?.getIcon(), mainItem?.getNotify()!!, mainItem?.getColor())
                }else{
                    FragmentRecycleViewItems(0, NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day,mainItem?.getIcon(), 0, mainItem?.getColor())
                }
            }
            intent.putExtra("ItemToAdd", item)
            intent.putExtra("Mode", mode)
            startActivity(intent)
            finish()
        }
        EditButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            mainItem?.setTimeEnd(EndTimeTextView.text.toString())
            mainItem?.setTimeStart(StartTimeTextView.text.toString())
            mainItem?.setName(NameEditText.text.toString())
            if (mainItem!!.getName()!!.isEmpty()){
                Snackbar.make(it,"Enter a task name", Snackbar.LENGTH_LONG)
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                        .show()
            }else if(mainItem!!.getColor()==null) {
                Snackbar.make(it,"Select a color", Snackbar.LENGTH_LONG)
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                        .show()
            }else if(mainItem!!.getIcon()==null) {
                Snackbar.make(it,"Select a Icon", Snackbar.LENGTH_LONG)
                        .setTextColor(resources.getColor(R.color.white))
                        .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                        .show()
            }else{
                dbHandler!!.updateItem(day!!, mainItem!!.getId(),mainItem!!)
                intent.putExtra("Day", day)
                startActivity(intent)
                finish()
            }
        }
        DeleteButton.setOnClickListener{
            dbHandler!!.deleteItem(day!!, mainItem!!.getId())
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Day", day)
            startActivity(intent)
            finish()
        }
        PickIconButton.setOnClickListener {
            val intent = Intent(this, SelectIcon::class.java)
            val item = if (mainItem?.getColor()!=null) {
                if(mainItem != null) {
                    FragmentRecycleViewItems(mainItem!!.getId(), NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day,mainItem?.getIcon(), mainItem?.getNotify()!!, mainItem?.getColor())
                }else{
                    FragmentRecycleViewItems(0, NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day, mainItem?.getIcon(), 0, mainItem?.getColor())
                }
            } else{
                if(mainItem != null) {
                    FragmentRecycleViewItems(mainItem!!.getId(), NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day,mainItem?.getIcon(), mainItem?.getNotify()!!, mainItem?.getColor())
                }else{
                    FragmentRecycleViewItems(0, NameEditText.text.toString(), StartTimeTextView.text.toString(), EndTimeTextView.text.toString(), day,mainItem?.getIcon(), 0, mainItem?.getColor())
                }
            }
            intent.putExtra("ItemToAdd", item)
            intent.putExtra("Mode", mode)
            startActivity(intent)
            finish()
        }
    }

    @SuppressLint("ResourceAsColor")
    fun refreshUI(){
        if(mainItem?.getTimeStart() == null) {
            mainItem?.setTimeStart(String.format("%02d:%02d",dbHandler!!.getLastTaskEndTime(day!!).split(":")[0].toInt(),dbHandler!!.getLastTaskEndTime(day!!).split(":")[1].toInt()))
            StartTimeTextView.text = String.format("%02d:%02d",dbHandler!!.getLastTaskEndTime(day!!).split(":")[0].toInt(),dbHandler!!.getLastTaskEndTime(day!!).split(":")[1].toInt())
        }else{
            StartTimeTextView.text = mainItem!!.getTimeStart()
        }

        if(mainItem?.getTimeEnd() == null) {
            mainItem?.setTimeEnd(String.format("%02d:%02d",dbHandler!!.getLastTaskEndTime(day!!).split(":")[0].toInt() + 1,dbHandler!!.getLastTaskEndTime(day!!).split(":")[1].toInt()))
            EndTimeTextView.text = String.format("%02d:%02d",dbHandler!!.getLastTaskEndTime(day!!).split(":")[0].toInt() + 1,dbHandler!!.getLastTaskEndTime(day!!).split(":")[1].toInt())
        }else{
            EndTimeTextView.text = mainItem!!.getTimeEnd()
        }

        if(mainItem?.getName() != null){
            NameEditText.setText(mainItem?.getName())
        }
        if (mainItem?.getColor() != null) {
            //llColorCardView.setBackgroundResource(mainItem?.getColor()!!)
            llColorCardView.setBackgroundColor(Color.parseColor(mainItem?.getColor()))
        }
        if(mainItem?.getIcon() != null){
            AddTaskIcon.setImageResource(resources.getIdentifier(mainItem?.getIcon(),"drawable", "com.nicknterm.todolist"))
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
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showTimerPickerDialog(text: TextView){
        var result = text.text
        val timePickerDialog = Dialog(this)
        timePickerDialog.setContentView(R.layout.time_picker_dialog)
        timePickerDialog.TimePickerInDialog.setIs24HourView(true)
        timePickerDialog.TimePickerInDialog.hour = text.text.split(":")[0].toInt()
        timePickerDialog.TimePickerInDialog.minute = text.text.split(":")[1].toInt()
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

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Day", day)
        startActivity(intent)
        finish()
    }
}