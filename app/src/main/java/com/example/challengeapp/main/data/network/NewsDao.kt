package com.example.challengeapp.main.data.network

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.challengeapp.main.data.model.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM News ")
    suspend fun getAllFavoriteNews(): List<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteNews(news: News)

    @Delete
    suspend fun deleteFavoriteNews (news: News)
}