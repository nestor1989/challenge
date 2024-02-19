package com.example.challengeapp.main.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeapp.R
import com.example.challengeapp.databinding.FragmentHomeBinding
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.ui.adapter.NewsAdapter
import com.example.challengeapp.main.ui.main.MainViewModel
import com.example.challengeapp.main.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
                    NewsAdapter.OnNewsClickListener
    {

    private var _binding: FragmentHomeBinding? = null
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
        setObservers()
    }

    private fun setUp(){
        mainViewModel.subtitle.postValue(getString(R.string.most_viewed))
    }

    private fun setObservers(){
        mainViewModel.fetchMostPopular(Constants.VIEWED).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    // Show loading
                }

                is Resource.Success -> {
                    Log.d("HomeFragment", "Most popular: ${result.data}")
                    initAdapter(result.data)
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

    override fun onNewsClick(news: News) {
        TODO("Not yet implemented")
    }

    private fun initAdapter(list : List<News>){
        val appContext = requireContext()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager= LinearLayoutManager(appContext)
        binding.recyclerView.adapter= NewsAdapter(requireContext(), list, this)
    }
}