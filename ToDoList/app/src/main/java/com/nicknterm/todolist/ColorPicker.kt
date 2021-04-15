package com.nicknterm.todolist

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceManager
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_color_picker.*

class ColorPicker : AppCompatActivity() {
    var finalColor: Int? = null
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
        setSupportActionBar(ToolBarColorPicker)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
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
            goToAddTask(it)
        }
        ColorPickerCustom.setOnClickListener {
            ColorPickerDialog
                    .Builder(this)        				// Pass Activity Instance
                    .setTitle("Pick Theme")           	// Default "Choose Color"
                    .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                    .setDefaultColor("#000000")     // Pass Default Color
                    .setColorListener { color, colorHex ->
                        deselectAll()
                        ColorPickerCustom.setBackgroundResource(R.drawable.color_selected_in_color_picker)
                        ColorPickerCustom.setBackgroundColor(Color.parseColor(colorHex))
                        finalColor = null
                        item?.setColor(colorHex)
                    }
                    .show()
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, AddTasksInRecycleView::class.java)
        intent.putExtra("Day", item?.getDay())
        intent.putExtra("ItemToAdd", item)
        intent.putExtra("Mode", mode)
        startActivity(intent)
        finish()
    }
    private fun goToAddTask(v: View){
        val intent = Intent(this, AddTasksInRecycleView::class.java)
        if(item?.getColor()==null && finalColor!= null) {
            item?.setColor(resources.getString(finalColor!!))
        }
        if(item?.getColor()!=null) {
            intent.putExtra("Day", item?.getDay())
            intent.putExtra("ItemToAdd", item)
            intent.putExtra("Mode", mode)
            startActivity(intent)
            finish()
        }else{
            Snackbar.make(v,"Select a color", Snackbar.LENGTH_LONG)
                    .setTextColor(resources.getColor(R.color.white))
                    .setBackgroundTint(resources.getColor(R.color.PrimaryBackgroundDark))
                    .show()
        }
    }
    private fun deselectAll(){
        ColorPickerRed.setBackgroundResource(0)
        ColorPickerBlue.setBackgroundResource(0)
        ColorPickerCyan.setBackgroundResource(0)
        ColorPickerGreen.setBackgroundResource(0)
        ColorPickerLime.setBackgroundResource(0)
        ColorPickerOrange.setBackgroundResource(0)
        ColorPickerYellow.setBackgroundResource(0)
        item?.setColor(null)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}