package com.nicknterm.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object{
        // Change DATABASE_VERSION every time you change the structure of the database
        // For example if you add another column, change the DATABASE_VERSION
        // Plus the onUpgrade function is going to get called so change that so the user doesn't loses all its data
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ToDoListDatabase"
        private const val TABLE_NAME = "ToDoList"

        // Table columns
        private const val KEY_ID = "id"
        private const val KEY_ELEMENT_ID = "local_id"
        private const val KEY_DAY = "day"
        private const val KEY_TASK_NAME = "name"
        private const val KEY_TIME_START = "starts"
        private const val KEY_TIME_END = "ends"
        private const val KEY_TASK_COLOR = "color"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = ("CREATE TABLE $TABLE_NAME ($KEY_ID INTEGER PRIMARY KEY, $KEY_ELEMENT_ID INT, $KEY_DAY TEXT, $KEY_TASK_NAME TEXT, $KEY_TIME_START TEXT, $KEY_TIME_END TEXT, $KEY_TASK_COLOR INT)")
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun tasksInDay(day: String): ArrayList<FragmentRecycleViewItems>{
        val sql = ("SELECT * FROM $TABLE_NAME WHERE $KEY_DAY='$day'")
        val db = this.readableDatabase
        val cursor: Cursor

        try{
            cursor = db.rawQuery(sql, null)
        } catch (e: SQLException){
            db.execSQL(sql)
            return ArrayList()
        }

        //var id:Int
        var localId: Int
        var name: String
        var endTime: String
        var startTime: String
        var color: Int
        val finalList:ArrayList<FragmentRecycleViewItems> = ArrayList<FragmentRecycleViewItems>()
        if(cursor.moveToFirst()){
            do{
                //id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                localId = cursor.getInt(cursor.getColumnIndex(KEY_ELEMENT_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_TASK_NAME))
                endTime = cursor.getString(cursor.getColumnIndex(KEY_TIME_END))
                startTime = cursor.getString(cursor.getColumnIndex(KEY_TIME_START))
                color = cursor.getInt(cursor.getColumnIndex(KEY_TASK_COLOR))

                val item = FragmentRecycleViewItems(localId, name, startTime, endTime, day, color)
                finalList.add(item)
            }while(cursor.moveToNext())
        }
        return finalList
    }

    fun addTaskInDay(item:FragmentRecycleViewItems): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_ELEMENT_ID, item.getId())
        contentValues.put(KEY_DAY, item.getDay())
        contentValues.put(KEY_TASK_NAME, item.getName())
        contentValues.put(KEY_TIME_END, item.getTimeEnd())
        contentValues.put(KEY_TIME_START, item.getTimeStart())
        contentValues.put(KEY_TASK_COLOR, item.getColor())

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    fun updateItem(day: String, id: Int, item: FragmentRecycleViewItems){

        val values = ContentValues()

        values.put(KEY_ELEMENT_ID, item.getId())
        values.put(KEY_DAY, item.getDay())
        values.put(KEY_TASK_NAME, item.getName())
        values.put(KEY_TIME_END, item.getTimeEnd())
        values.put(KEY_TIME_START, item.getTimeStart())
        values.put(KEY_TASK_COLOR, item.getColor())

        val db = this.writableDatabase
        db.update(TABLE_NAME, values, "$KEY_DAY='$day' AND $KEY_ELEMENT_ID=$id", arrayOf())
    }

    fun getElementId(day:String): Int{
        val items = tasksInDay(day)
        var maxId = 0
        for (i in items){
            if(i.getId()>maxId){
                maxId = i.getId()
            }
        }
        return maxId
    }
}