package com.example.challengeapp.main.data.datasource

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News

interface DataSource {
    suspend fun getMostPopular(searchBy: String, period: String): Resource<List<News>>

    suspend fun insertNews(news: News)

    suspend fun getFavoriteNews(): Resource<List<News>>

    suspend fun deleteNews (news: News)
}