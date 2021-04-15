package com.nicknterm.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.fragment_recycle_view.*
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
            dayRecycleViewItemsList.sortBy { it.getTimeStart() }
        }
        adapter = context?.let { MainListRecycleViewAdapter(dayRecycleViewItemsList, it) }
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recycle_view, container, false)
        view.FragmentRecycleView.layoutManager = LinearLayoutManager(context)
        view.FragmentRecycleView.adapter = adapter
        (view.FragmentRecycleView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        if(dayRecycleViewItemsList.size != 0) {
            view.NoEventLayout.visibility = View.GONE
        }
        return view
    }

    companion object {

    }
}