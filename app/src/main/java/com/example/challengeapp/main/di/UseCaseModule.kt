package com.example.challengeapp.main.di

import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.domain.FetchMostPopularUseCase
import com.example.challengeapp.main.domain.FetchMostPopularUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideFetchMostPopularUseCase(repo: Repo): FetchMostPopularUseCase {
        return FetchMostPopularUseCaseImpl(repo)
    }
}