package com.example.challengeapp.main.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.challengeapp.databinding.FragmentHomeBinding
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUp()
        setObservers()

        return binding.root
    }

    private fun setUp(){
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

    private fun setObservers(){
        mainViewModel.fetchMostPopular().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    // Show loading
                }

                is Resource.Success -> {
                    Log.d("HomeFragment", "Most popular: ${result.data}")
                }

                is Resource.Failure -> {
                    Log.d("HomeFragment", "Error: ${result.exception}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}