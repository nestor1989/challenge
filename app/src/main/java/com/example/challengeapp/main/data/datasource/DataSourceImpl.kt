package com.example.challengeapp.main.data.datasource

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.network.NewsDao
import com.example.challengeapp.main.data.network.WebService
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val webService: WebService,
    private val newsDao: NewsDao
): DataSource {
    override suspend fun getMostPopular(searchBy: String, period: String): List<News> {
        return webService.mostPopular(searchBy, period).results
    }

    override suspend fun insertNews(news: News) {
        newsDao.addFavoriteNews(news)
    }

    override suspend fun getFavoriteNews(): List<News> {
        return newsDao.getAllFavoriteNews()
    }

    override suspend fun deleteNews(news: News) {
        newsDao.deleteFavoriteNews(news)
    }
}