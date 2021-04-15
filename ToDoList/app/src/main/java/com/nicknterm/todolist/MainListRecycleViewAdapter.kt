package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.android.material.internal.ContextUtils.getActivity
import kotlinx.android.synthetic.main.main_recycle_view_item.view.*


class MainListRecycleViewAdapter(private val items: ArrayList<FragmentRecycleViewItems>, private val context: Context) :
        RecyclerView.Adapter<MainListRecycleViewAdapter.ViewHolder>() {
    private var openedTask: Int = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeText: TextView = view.TimeItemText
        val nameText: TextView = view.NameItemText
        val mainLL: LinearLayout = view.mainLL
        val editButton: TextView = view.EditItemButton
        val buttonLL: LinearLayout = view.buttonLL
        val endTimeText: TextView = view.EndTimeItemText
        val iconImage: ImageView = view.ItemIcon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycle_view_item, parent, false))
    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.timeText.text = "${item.getTimeStart()}"
        holder.endTimeText.text = "${item.getTimeEnd()}"
        holder.nameText.text = item.getName()
        val iconName = item.getIcon()
        holder.iconImage.setImageResource(holder.itemView.context.resources.getIdentifier(iconName, "drawable", "com.nicknterm.todolist"))
        if (openedTask != position) {
            holder.buttonLL.visibility = View.GONE
        } else {
            holder.buttonLL.visibility = View.VISIBLE
        }
        if (item.getColor() != null) {
            holder.mainLL.setBackgroundColor(Color.parseColor(item.getColor()))
        }
        holder.mainLL.setOnClickListener {
            if (holder.buttonLL.visibility == View.VISIBLE) {
                openedTask = -1
                notifyItemChanged(position)
            } else {
                notifyItemChanged(openedTask)
                openedTask = position
                notifyItemChanged(position)
            }
        }
        holder.editButton.setOnClickListener {
            val intent = Intent(context, AddTasksInRecycleView::class.java)
            intent.putExtra("Day", item.getDay())
            intent.putExtra("Mode", "Edit")
            intent.putExtra("ItemToAdd", item)
            startActivity(context, intent, null)
            getActivity(context)!!.finish()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}