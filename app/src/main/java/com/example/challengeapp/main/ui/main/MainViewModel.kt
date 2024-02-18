package com.example.challengeapp.main.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repo,
    @ApplicationContext private val context: Context
): ViewModel() {

    fun fetchMostPopular(searchBy: String) = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getMostPopular(searchBy))
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

}