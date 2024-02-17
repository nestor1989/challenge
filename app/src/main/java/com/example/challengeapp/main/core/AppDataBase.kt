package com.example.challengeapp.main.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.challengeapp.main.data.model.Works
import com.example.challengeapp.main.data.network.WorksDao

@Database(entities = [Works::class], version = 1)
abstract class AppDataBase: RoomDatabase() {

    abstract fun worksDao(): WorksDao

}