package com.nicknterm.todolist

class FragmentRecycleViewItems(private var id: Int,private var Name:String, private var TimeStart: String, private var TimeEnd:String, private var Day: String) {

    fun getId():Int{
        return id
    }

    fun getName():String{
        return Name
    }

    fun getTimeStart():String{
        return TimeStart
    }

    fun getTimeEnd(): String{
        return TimeEnd
    }

    fun getDay(): String{
        return Day
    }
}
