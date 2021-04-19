package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*


class MainActivity : AppCompatActivity() {
    private var dayToStart: Int = 0
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    private val channelId = "com.nicknterm.todolist"
    private val description = "Test notification"
    private var notify = false
    private var minutesToNotify = 5

    @SuppressLint("SimpleDateFormat")
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
            "purple" -> setTheme(R.style.MyTheme_purple)
            else -> setTheme(R.style.MyTheme_cyan)
        }
        if (sharedPref.getBoolean("notification", true)) {
            notify = true
        }
        minutesToNotify = sharedPref.getString("notificationTime", "0")!!.toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        dayToStart = if (intent.getStringExtra("Day") != null) {
            dayToInt(intent.getStringExtra("Day")!!)
        } else {
            val sdf = SimpleDateFormat("EEEE")
            dayToInt(sdf.format(Date()))
        }
        setSupportActionBar(MainActivityToolBar)
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Monday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Tuesday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Wednesday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Thursday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Friday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Saturday"))
        MainActivityTabLayout.addTab(MainActivityTabLayout.newTab().setText("Sunday"))

        MainActivityTabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val tabAdapter = TabAdapter(this, supportFragmentManager, MainActivityTabLayout.tabCount)
        MainActivityViewPager.adapter = tabAdapter

        MainActivityViewPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                MainActivityTabLayout
            )
        )

        MainActivityViewPager.currentItem = dayToStart
        MainActivityTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                MainActivityViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        AddButton.setOnClickListener {
            val intent = Intent(this, AddTasksInRecycleView::class.java)
            intent.putExtra("Day", intToDay(MainActivityViewPager.currentItem))
            startActivity(intent)
            finish()
        }

        SettingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }

        if (notify) {
            createChannel()
            createNotifications()
        }

    }

    override fun onBackPressed() {
        finish()
    }

    private fun dayToInt(day: String): Int {
        return when (day) {
            "Monday" -> 0
            "Tuesday" -> 1
            "Wednesday" -> 2
            "Thursday" -> 3
            "Friday" -> 4
            "Saturday" -> 5
            "Sunday" -> 6
            else -> 0
        }
    }

    private fun intToDay(int: Int): String {
        return when (int) {
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            6 -> "Sunday"
            else -> ""
        }
    }

    private fun createChannel() {
        notificationChannel = NotificationChannel(
            channelId,
            description,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(false)
        notificationManager.createNotificationChannel(notificationChannel)

    }

    private fun createNotifications() {
        val myDB = DatabaseHandler(this)
        val tasks = myDB.getNotificationItems()
        var id = -1
        for (task in tasks) {
            id +=1
            val alarmManager = this.getSystemService(ALARM_SERVICE) as AlarmManager
            val cal = Calendar.getInstance()
            Log.i(
                "Mymsg",
                "Date First ${task.getName()} ${
                    SimpleDateFormat.getDateTimeInstance().format(cal.time)
                } (${cal.get(Calendar.DAY_OF_WEEK)})"
            )
            cal.set(Calendar.HOUR, task.getTimeStart()!!.split(":")[0].toInt())
            cal.set(Calendar.MINUTE, task.getTimeStart()!!.split(":")[1].toInt())
            cal.set(Calendar.SECOND, 0)

            Log.i(
                "Mymsg",
                "Date second ${task.getName()} ${
                    SimpleDateFormat.getDateTimeInstance().format(cal.time)
                } Day of Week(${cal.get(Calendar.DAY_OF_WEEK)}) Day of Task(${(dayToInt(task.getDay()!!) + 2)})"
            )
            var dayInt = (dayToInt(task.getDay()!!) + 2) % 7
            if(dayInt == 0) { dayInt = 7}
            if (dayInt < cal.get(Calendar.DAY_OF_WEEK)) {
                cal.add(Calendar.WEEK_OF_MONTH, 1)
                cal.set(Calendar.DAY_OF_WEEK, (dayToInt(task.getDay()!!) + 2) % 7)
            } else if (dayInt != cal.get(Calendar.DAY_OF_WEEK)) {
                cal.set(Calendar.DAY_OF_WEEK, (dayToInt(task.getDay()!!) + 2) % 7)
            }


            var finalMillis: Long = cal.timeInMillis - Calendar.getInstance().timeInMillis
            if (finalMillis > 0) {
                Log.i(
                    "Mymsg",
                    "Date ${
                        SimpleDateFormat.getDateTimeInstance().format(cal.time)
                    } (${cal.get(Calendar.DAY_OF_WEEK)})"
                )
                Log.i("Mymsg", "time before $minutesToNotify and millis $finalMillis")
                val intent = Intent(this, NotificationActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("id", id)
                bundle.putParcelable("task", task)
                intent.putExtra("bundle", bundle)
                val pendingIntent =
                    PendingIntent.getBroadcast(this, id , intent, 0)
                alarmManager.setExact(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + finalMillis - (minutesToNotify * 60 * 1000),
                    pendingIntent
                )
            }else{
                cal.add(Calendar.DAY_OF_MONTH, 7)
                Log.i(
                    "Mymsg",
                    "Date ${
                        SimpleDateFormat.getDateTimeInstance().format(cal.time)
                    } (${cal.get(Calendar.DAY_OF_WEEK)})"
                )
                finalMillis = cal.timeInMillis - Calendar.getInstance().timeInMillis
                Log.i("Mymsg", "time before $minutesToNotify and millis $finalMillis")
            }
        }
    }

}