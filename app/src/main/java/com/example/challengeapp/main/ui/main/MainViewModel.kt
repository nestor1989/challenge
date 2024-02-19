package com.example.challengeapp.main.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repo,
    @ApplicationContext private val context: Context
): ViewModel() {

    var subtitle: MutableLiveData<String> = MutableLiveData()

    var favList: MutableLiveData<List<News>> = MutableLiveData()

    fun fetchMostPopular(searchBy: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getMostPopular(searchBy))
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

    fun addedToFavorite (news: News) {
        CoroutineScope(Dispatchers.Main).launch {
            repo.addedNewsToFav(news)
        }
    }

    fun deleteFavorite (news: News) {
        CoroutineScope(Dispatchers.Main).launch {
            repo.deleteFavorite(news)
        }
    }

    fun getFavorites() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getNewsFav())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}