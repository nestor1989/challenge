package com.example.challengeapp.main.data.datasource

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News

interface DataSource {
    suspend fun getMostPopular(searchBy: String): Resource<List<News>>
}