package com.nicknterm.todolist

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import java.util.*


class NotificationActivity : BroadcastReceiver() {
    lateinit var builder: Notification.Builder
    private val channelId = "com.nicknterm.todolist"
    private var task: FragmentRecycleViewItems? = null
    private val quotes = arrayListOf(
        "All our dreams can come true, if we have the courage to pursue them.",
        "The secret of getting ahead is getting started.",
        "Don’t tell people your plans. Show them your results.",
        "No pressure, no diamonds.",
        "We can do anything we want to if we stick to it long enough.",
        "Stay foolish to stay sane.",
        "When nothing goes right, go left.",
        "Try Again. Fail again. Fail better.",
        "Impossible is for the unwilling.",
        "Once you choose hope, anything’s possible.",
        "I can and I will.",
        "Take the risk or lose the chance.",
        "There is no saint without a past, no sinner without a future.",
        "Good things happen to those who hustle.",
        "Solitary trees, if they grow at all, grow strong.",
        "Go forth on your path, as it exists only through your walking.",
        "The bird a nest, the spider a web, man friendship.",
        "He who is brave is free.",
        "Prove them wrong.",
        "The fastest road to meaning and success: choose one thing and go all-in.",
        "Screw it, let’s do it.",
        "Keep going. Be all in.",
        "Dream big.",
        "Leave no stone unturned.",
        "Do it with passion or not at all."
    )

    override fun onReceive(context: Context, intent: Intent) {
        val mNotificationManager = NotificationManagerCompat.from(context)
        var notifyID: Int
        val rand = Random()
        val bundle = intent.getBundleExtra("bundle")
        task = bundle?.getParcelable("task")
        notifyID = bundle?.getSerializable("id") as Int
        builder = Notification.Builder(context, channelId)
            .setSmallIcon(R.drawable.icon)//context.resources.getIdentifier(intent.getStringExtra("Icon"), "drawable", "com.nicknterm.todolist")
            .setContentTitle(task?.getName())
            .setLargeIcon(context.resources.getDrawable(context.resources.getIdentifier(
                task?.getIcon(),
                "drawable",
                "com.nicknterm.todolist"
            )).toBitmap() )
            .setContentText(quotes[rand.nextInt(quotes.size)])
            .setAutoCancel(true)
        mNotificationManager.notify(
            notifyID,
            builder.build()
        )
    }
}