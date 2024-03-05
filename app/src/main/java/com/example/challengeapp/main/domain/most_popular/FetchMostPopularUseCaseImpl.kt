package com.example.challengeapp.main.domain.most_popular

import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.data.model.News
import javax.inject.Inject

class FetchMostPopularUseCaseImpl @Inject constructor(
    private val repo: Repo
) : FetchMostPopularUseCase {
    override suspend operator fun invoke(searchBy: String, period: String): List<News> {
        return repo.getMostPopular(searchBy, period)
    }
}