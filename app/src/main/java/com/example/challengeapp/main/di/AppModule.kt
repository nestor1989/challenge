package com.example.challengeapp.main.di

import android.content.Context
import androidx.room.Room
import com.example.challengeapp.main.core.AppDataBase
import com.example.challengeapp.main.data.datasource.DataSource
import com.example.challengeapp.main.data.datasource.DataSourceImpl
import com.example.challengeapp.main.data.network.WebService
import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.data.repo.RepoImpl
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideWebService(): WebService {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.thingiverse.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        return retrofit.create(WebService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDataBase::class.java,"tabla cosas"
    ).build()

    @Singleton
    @Provides
    fun provideThingsDao(db: AppDataBase) = db.worksDao()

}