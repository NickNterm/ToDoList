package com.nicknterm.todolist

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat


class NotificationActivity : BroadcastReceiver() {
    lateinit var builder: Notification.Builder
    private val channelId = "com.nicknterm.todolist"
    private var task: FragmentRecycleViewItems? = null

    override fun onReceive(context: Context, intent: Intent) {
        val mNotificationManager = NotificationManagerCompat.from(context)
        var notifyID = 1

        Log.i("Mymsg", "end")
        val bundle = intent.getBundleExtra("bundle")
        task = bundle?.getParcelable("task")
        notifyID = bundle?.getSerializable("id") as Int
        builder = Notification.Builder(context, channelId)
            .setSmallIcon(context.resources.getIdentifier(task?.getIcon(), "drawable", "com.nicknterm.todolist"))//context.resources.getIdentifier(intent.getStringExtra("Icon"), "drawable", "com.nicknterm.todolist")
            .setContentTitle(task?.getName())
            .setContentText("Ma ti geniada to odin, thn kapa toy batman toy robin")
            .setAutoCancel(true)
        mNotificationManager.notify(
            notifyID,
            builder.build()
        )
    }
}