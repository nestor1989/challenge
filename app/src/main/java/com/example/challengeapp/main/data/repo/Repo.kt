package com.example.challengeapp.main.data.repo

import com.example.challengeapp.main.data.model.News

interface Repo {
    suspend fun getMostPopular(searchBy: String, period: String): List<News>
    suspend fun addedNewsToFav(news: News)
    suspend fun getNewsFav(): List<News>
    suspend fun deleteFavorite(news: News)
}