package com.nicknterm.todolist

import android.os.Parcel
import android.os.Parcelable

class FragmentRecycleViewItems(private var id: Int, private var Name: String?, private var TimeStart: String?, private var TimeEnd: String?, private var Day: String?, private var Color: Int):Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()) {
    }

    fun getId():Int{
        return id
    }

    fun getName():String?{
        return Name
    }

    fun getTimeStart():String?{
        return TimeStart
    }

    fun getTimeEnd(): String?{
        return TimeEnd
    }

    fun getDay(): String?{
        return Day
    }

    fun getColor(): Int?{
        return Color
    }
    fun setId(v: Int){
        id = v
    }

    fun setName(v: String){
        Name = v
    }

    fun setTimeStart(v: String){
        TimeStart = v
    }

    fun setTimeEnd(v: String){
        TimeEnd = v
    }

    fun setDay(v: String){
        Day = v
    }

    fun setColor(v: Int){
        Color = v
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(Name)
        parcel.writeString(TimeStart)
        parcel.writeString(TimeEnd)
        parcel.writeString(Day)
        parcel.writeInt(Color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FragmentRecycleViewItems> {
        override fun createFromParcel(parcel: Parcel): FragmentRecycleViewItems {
            return FragmentRecycleViewItems(parcel)
        }

        override fun newArray(size: Int): Array<FragmentRecycleViewItems?> {
            return arrayOfNulls(size)
        }
    }
}
