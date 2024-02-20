package com.example.challengeapp.main.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.challengeapp.R
import com.example.challengeapp.databinding.ActivityMainBinding
import com.example.challengeapp.main.core.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenSplash = installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenSplash.setKeepOnScreenCondition {false}

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        setSubtitleObserve()

        setUpFav()

    }

    private fun setSubtitleObserve(){
        mainViewModel.subtitle.observe(this) {
            binding.tvFragment.setText(it)
        }
    }

    fun setUpFav(){
        mainViewModel.getFavorites().observe(this) { result ->

            when(result){
                is Resource.Loading->{
                }
                is Resource.Success->{
                    mainViewModel.favList.postValue(result.data)
                }
                is Resource.Failure->{
                    Toast.makeText(applicationContext, result.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}