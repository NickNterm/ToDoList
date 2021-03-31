package com.nicknterm.todolist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recycle_view.view.*

class RecycleViewFragment(private var dayPar: String) : Fragment() {
    private var dayRecycleViewItemsList: ArrayList<FragmentRecycleViewItems> = ArrayList<FragmentRecycleViewItems>()
    private var adapter: MainListRecycleViewAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val db = context?.let { DatabaseHandler(it) }
        if (db != null) {
            dayRecycleViewItemsList = db.tasksInDay(dayPar)
        }
        adapter = context?.let { MainListRecycleViewAdapter(dayRecycleViewItemsList, it) }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recycle_view, container, false)
        view.FragmentRecycleView.layoutManager = LinearLayoutManager(context)
        view.FragmentRecycleView.adapter = adapter
        view.day.text = dayPar
        return view
    }

    companion object {

    }
}