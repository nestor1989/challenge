package com.example.challengeapp.main.data.repo

import com.example.challengeapp.main.data.datasource.DataSource
import com.example.challengeapp.main.data.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoImpl
@Inject constructor(private val dataSource: DataSource)
:Repo {
    override suspend fun getMostPopular(searchBy: String, period: String):List<News> {
        return dataSource.getMostPopular(searchBy, period)
    }

    override suspend fun addedNewsToFav(news: News) {
        dataSource.insertNews(news)
    }

    override suspend fun getNewsFav(): List<News> {
        return dataSource.getFavoriteNews()
    }

    override suspend fun deleteFavorite(news: News) {
        dataSource.deleteNews(news)
    }

    override suspend fun getFlowPopular(
        searchBy: String,
        period: String
    ): Flow<List<News>> {
        return dataSource.getFlowPopular(searchBy, period)
    }
}