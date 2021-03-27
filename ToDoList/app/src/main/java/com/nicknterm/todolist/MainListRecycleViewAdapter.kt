package com.nicknterm.todolist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_recycle_view_item.view.*

class MainListRecycleViewAdapter(private val items: ArrayList<FragmentRecycleViewItems>, private val context: Context):
        RecyclerView.Adapter<MainListRecycleViewAdapter.ViewHolder>() {
    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val timeText:TextView = view.TimeItemText
        val nameText:TextView = view.NameItemText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycle_view_item,parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item= items[position]
        holder.timeText.text = "${item.getTimeStart()}-${item.getTimeEnd()}"
        holder.nameText.text = item.getName()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}