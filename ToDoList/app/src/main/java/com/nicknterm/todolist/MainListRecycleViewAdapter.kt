package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_recycle_view_item.view.*

class MainListRecycleViewAdapter(private val items: ArrayList<FragmentRecycleViewItems>, private val context: Context):
        RecyclerView.Adapter<MainListRecycleViewAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val timeText:TextView = view.TimeItemText
        val nameText:TextView = view.NameItemText
        val mainLL: LinearLayout = view.mainLL
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycle_view_item,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= items[position]
        holder.timeText.text = "${item.getTimeStart()}"
        holder.nameText.text = item.getName()
        if(item.getColor()!=1) {
            val color = item.getColor()
            holder.mainLL.setBackgroundResource(color!!)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}