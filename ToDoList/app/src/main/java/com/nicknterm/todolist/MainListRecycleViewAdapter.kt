package com.nicknterm.todolist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.recyclerview.widget.RecyclerView
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycle_view_item, parent, false))
    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.timeText.text = "${item.getTimeStart()}"
        holder.nameText.text = item.getName()
        if (openedTask != position) {
            holder.buttonLL.animate()
                    .x(holder.buttonLL.width.toFloat())
                    .duration = 1
            holder.buttonLL.visibility = View.GONE

        } else {
            holder.buttonLL.visibility = View.VISIBLE

            holder.buttonLL.translationX = holder.buttonLL.width.toFloat()
            holder.buttonLL.animate()
                    .translationX(0f)
                    .duration = 300
        }
        if (item.getColor() != 1) {
            val color = item.getColor()
            holder.mainLL.setBackgroundResource(color!!)
        }
        holder.mainLL.setOnClickListener {
            if (holder.buttonLL.visibility == View.VISIBLE) {
                openedTask = -1

                holder.buttonLL.animate()
                        .translationX(holder.buttonLL.width.toFloat())
                        .withEndAction {
                            holder.buttonLL.visibility = View.GONE
                            notifyDataSetChanged()
                        }.duration = 300
            } else {
                openedTask = position
                notifyDataSetChanged()
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