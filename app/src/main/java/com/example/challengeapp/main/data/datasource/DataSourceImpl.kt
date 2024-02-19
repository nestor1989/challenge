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
    override suspend fun getMostPopular(searchBy: String): Resource<List<News>> {
        return Resource.Success(webService.mostPopular(searchBy).results)
    }

    override suspend fun insertNews(news: News) {
        newsDao.addFavoriteNews(news)
    }

    override suspend fun getFavoriteNews(): Resource<List<News>> {
        return Resource.Success(newsDao.getAllFavoriteNews())
    }

    override suspend fun deleteNews(news: News) {
        newsDao.deleteFavoriteNews(news)
    }
}