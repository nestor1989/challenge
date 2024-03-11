package com.example.challengeapp.main.data.datasource

import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.network.NewsDao
import com.example.challengeapp.main.data.network.WebService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun getFlowPopular(searchBy: String, period: String): Flow<List<News>> = flow {
        while(true) {
            val latestNews = webService.mostPopular(searchBy, period).results
            emit(latestNews) // Emits the result of the request to the flow
            delay(10000)// Suspends the coroutine for some time
        }
    }

}