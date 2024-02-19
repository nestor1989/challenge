package com.example.challengeapp.main.utils

import androidx.room.TypeConverter
import com.example.challengeapp.main.data.model.Media
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromMediaList(mediaList: List<Media>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Media>>() {}.type
        return gson.toJson(mediaList, type)
    }

    @TypeConverter
    fun toMediaList(mediaListString: String): List<Media> {
        val gson = Gson()
        val type = object : TypeToken<List<Media>>() {}.type
        return gson.fromJson(mediaListString, type)
    }
}