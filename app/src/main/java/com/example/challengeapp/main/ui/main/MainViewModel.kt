package com.example.challengeapp.main.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.model.NewsDTO
import com.example.challengeapp.main.data.repo.Repo
import com.example.challengeapp.main.domain.mapper.GetNewsMapper
import com.example.challengeapp.main.domain.mapper.NewMapper
import com.example.challengeapp.main.domain.favorites.GetFavoritesUseCase
import com.example.challengeapp.main.domain.most_popular.FetchMostPopularUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repo,
    private val fetchMostPopularUseCase: FetchMostPopularUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getNewsMapper: GetNewsMapper,
    private val newMapper: NewMapper
): ViewModel() {

    var subtitle: MutableLiveData<String> = MutableLiveData()

    var favList: MutableLiveData<List<NewsDTO>> = MutableLiveData()
    var flowPopulars: MutableLiveData<List<NewsDTO>> = MutableLiveData()
    private val _uiState = MutableStateFlow<Resource<List<NewsDTO>>>(Resource.Loading())
    val uiState: StateFlow<Resource<List<NewsDTO>>> = _uiState

    fun fetchMostPopular(searchBy: String, period: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = fetchMostPopularUseCase(searchBy, period)
            emit(Resource.Success(getNewsMapper.mapToUI(result)))
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }
    fun fetchFlowMostPopular(searchBy: String, period: String) {
        viewModelScope.launch {
            repo.getFlowPopular(searchBy, period)
                .catch { exception -> notifyError(exception) }
                .flowOn(Dispatchers.IO)
                .collect { news ->
                    _uiState.value = Resource.Success(getNewsMapper.mapToUI(news))
                }
        }

    }

    private fun notifyError(exception: Throwable) {
        Log.e("MainViewModel", "Error: ${exception.message}")
    }

    fun addedToFavorite (news: NewsDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.addedNewsToFav(newMapper.mapToDomain(news))
        }
    }

    fun deleteFavorite (news: NewsDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.deleteFavorite(newMapper.mapToDomain(news))
        }
    }

    fun getFavorites() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = getFavoritesUseCase()
            emit(Resource.Success(getNewsMapper.mapToUI(result)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}