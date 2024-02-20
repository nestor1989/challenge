package com.example.challengeapp.main.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.domain.favorites.GetFavoritesUseCase
import com.example.challengeapp.main.domain.most_popular.FetchMostPopularUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repo,
    private val fetchMostPopularUseCase: FetchMostPopularUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
): ViewModel() {

    var subtitle: MutableLiveData<String> = MutableLiveData()

    var favList: MutableLiveData<List<News>> = MutableLiveData()

    fun fetchMostPopular(searchBy: String, period: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(fetchMostPopularUseCase.execute(searchBy, period))
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
            emit(getFavoritesUseCase.execute())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}