package com.example.challengeapp.main.di

import com.example.challengeapp.main.data.datasource.DataSource
import com.example.challengeapp.main.data.datasource.DataSourceImpl
import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.data.repo.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract fun bindRepoImpl(repoImpl: RepoImpl): Repo

    @Binds
    abstract fun bindDataSource(impl: DataSourceImpl): DataSource

}

