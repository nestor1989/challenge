package com.example.challengeapp.main.data.repo

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.datasource.DataSource
import com.example.challengeapp.main.data.model.News
import javax.inject.Inject

class RepoImpl
@Inject constructor(private val dataSource: DataSource)
:Repo {
    override suspend fun getMostPopular(searchBy: String): Resource<List<News>> {
        return dataSource.getMostPopular(searchBy)
    }

    override suspend fun addedNewsToFav(news: News) {
        dataSource.insertNews(news)
    }

    override suspend fun getNewsFav(): Resource<List<News>> {
        return dataSource.getFavoriteNews()
    }

    override suspend fun deleteFavorite(news: News) {
        dataSource.deleteNews(news)
    }
}