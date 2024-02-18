package com.example.challengeapp.main.data.datasource

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.network.WebService
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val webService: WebService,
): DataSource {
    override suspend fun getMostPopular(): Resource<List<News>> {
        return Resource.Success(webService.mostPopular().results)
    }
}