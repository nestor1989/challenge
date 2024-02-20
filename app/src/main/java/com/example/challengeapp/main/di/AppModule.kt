package com.example.challengeapp.main.di

import android.content.Context
import androidx.room.Room
import com.example.challengeapp.main.core.AppDataBase
import com.example.challengeapp.main.data.network.WebService
import com.example.challengeapp.main.utils.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Singleton
    @Provides
    fun provideWebService(): WebService {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
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
    fun provideThingsDao(db: AppDataBase) = db.newsDao()

}