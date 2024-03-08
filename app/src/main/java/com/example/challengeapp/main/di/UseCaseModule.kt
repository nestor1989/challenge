package com.example.challengeapp.main.di

import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.domain.mapper.GetNewsMapper
import com.example.challengeapp.main.domain.mapper.NewMapper
import com.example.challengeapp.main.domain.favorites.GetFavoritesUseCase
import com.example.challengeapp.main.domain.favorites.GetFavoritesUseCaseImpl
import com.example.challengeapp.main.domain.most_popular.FetchMostPopularUseCase
import com.example.challengeapp.main.domain.most_popular.FetchMostPopularUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetNewsMapper(): GetNewsMapper {
        return GetNewsMapper()
    }

    @Provides
    fun provideNewMapper(): NewMapper {
        return NewMapper()
    }

    @Provides
    fun provideFetchMostPopularUseCase(repo: Repo): FetchMostPopularUseCase {
        return FetchMostPopularUseCaseImpl(repo)
    }

    @Provides
    fun provideGetFavoritesUseCase(repo: Repo): GetFavoritesUseCase {
        return GetFavoritesUseCaseImpl(repo)
    }
}