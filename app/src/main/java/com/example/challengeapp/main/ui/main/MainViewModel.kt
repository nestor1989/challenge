package com.example.challengeapp.main.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.challengeapp.main.data.repo.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: Repo,
    //private val context: Context
): ViewModel() {
}