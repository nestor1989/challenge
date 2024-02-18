package com.example.challengeapp.main.data.repo

import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News

interface Repo {
    suspend fun getMostPopular(): Resource<List<News>>
}