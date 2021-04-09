package com.elbaitdesign.evapharmandroidtask.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromListToString(list:List<Int>):String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromStringToList(string:String):List<Int>{
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(string,listType)
    }
}