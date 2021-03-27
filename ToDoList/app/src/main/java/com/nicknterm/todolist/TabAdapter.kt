package com.nicknterm.todolist

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class TabAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                RecycleViewFragment("Monday")
            }
            1 -> {
                RecycleViewFragment("Tuesday")
            }
            2 -> {
                RecycleViewFragment("Wednesday")
            }
            3 -> {
                RecycleViewFragment("Thursday")
            }
            4 -> {
                RecycleViewFragment("Friday")
            }
            5 -> {
                RecycleViewFragment("Saturday")
            }
            6 -> {
                RecycleViewFragment("Sunday")
            }
            else -> getItem(position)
        }
    }
    override fun getCount(): Int {
        return totalTabs
    }
}