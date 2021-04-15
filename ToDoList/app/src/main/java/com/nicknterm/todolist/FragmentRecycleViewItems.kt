package com.nicknterm.todolist

import android.os.Parcel
import android.os.Parcelable

class FragmentRecycleViewItems(private var id: Int, private var Name: String?, private var TimeStart: String?, private var TimeEnd: String?, private var Day: String?, private var Icon: String?, private var Notify: Int, private var HexColor: String?):Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString()) {
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


    fun getIcon(): String? {
        return Icon
    }

    fun getNotify(): Int{
        return Notify
    }

    fun getColor():String? {
        return HexColor
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

    fun setIcon(v: String){
        Icon = v
    }

    fun setNotify(v: Int){
        Notify = v
    }

    fun setColor(v: String?){
        HexColor = v
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(Name)
        parcel.writeString(TimeStart)
        parcel.writeString(TimeEnd)
        parcel.writeString(Day)
        parcel.writeString(Icon)
        parcel.writeInt(Notify)
        parcel.writeString(HexColor)
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
