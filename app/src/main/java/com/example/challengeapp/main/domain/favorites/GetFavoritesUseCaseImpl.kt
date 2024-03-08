package com.example.challengeapp.main.domain.favorites
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.repo.Repo
import javax.inject.Inject

class GetFavoritesUseCaseImpl @Inject constructor(
    private val repo: Repo
): GetFavoritesUseCase{
    override suspend operator fun invoke(): List<News> {
        return repo.getNewsFav()
    }
}