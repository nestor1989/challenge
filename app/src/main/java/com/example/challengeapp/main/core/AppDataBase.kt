package com.example.challengeapp.main.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.network.NewsDao

@Database(entities = [News::class], version = 2)
abstract class AppDataBase: RoomDatabase() {

    abstract fun newsDao(): NewsDao

}