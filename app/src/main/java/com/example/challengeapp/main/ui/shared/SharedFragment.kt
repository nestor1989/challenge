package com.example.challengeapp.main.ui.shared

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeapp.R
import com.example.challengeapp.databinding.FragmentSharedBinding
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.ui.adapter.NewsAdapter
import com.example.challengeapp.main.ui.main.MainViewModel
import com.example.challengeapp.main.ui.modal.NewsModalFragment
import com.example.challengeapp.main.ui.modal.ProgressDialogFragment
import com.example.challengeapp.main.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedFragment : Fragment(),
    NewsAdapter.OnNewsClickListener,
    NewsModalFragment.OnArticleClickListener
    {

        private var _binding: FragmentSharedBinding? = null
        private val mainViewModel by activityViewModels<MainViewModel>()

        private val binding get() = _binding!!
        private lateinit var newsModalFragment: NewsModalFragment
        private lateinit var progressDialogFragment: ProgressDialogFragment

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            _binding = FragmentSharedBinding.inflate(inflater, container, false)
            val root: View = binding.root

            return root
        }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            setUp()
            setObservers()
        }

        private fun setUp(){
            mainViewModel.subtitle.postValue(getString(R.string.most_shared))
        }

        private fun setObservers(){
            progressDialogFragment = ProgressDialogFragment()
            val newProgress = progressDialogFragment.newInstance()

            mainViewModel.fetchMostPopular(Constants.SHARED).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Resource.Loading -> {
                        newProgress.show(activity?.supportFragmentManager!!, "progress dialog")
                    }

                    is Resource.Success -> {
                        initAdapter(result.data)
                        newProgress.dismiss()

                    }

                    is Resource.Failure -> {
                        Log.d("SharedFragment", "Error: ${result.exception}")
                        newProgress.dismiss()
                    }
                }
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        override fun onNewsClick(news: News) {
            modalInst(news)
        }

        private fun initAdapter(list : List<News>){
            val appContext = requireContext()
            val recyclerView = binding.recyclerView
            recyclerView.layoutManager= LinearLayoutManager(appContext)
            binding.recyclerView.adapter= NewsAdapter(requireContext(), list, this)
        }

        private fun modalInst(news: News){
            //val favorite = validateFav(news)
            news.favorite = false
            newsModalFragment = NewsModalFragment(news, this)
            val newInst = newsModalFragment.newInstance(news)
            newInst.show(activity?.supportFragmentManager!!, "newsmodal")
        }

        override fun onLikeClick(news: News) {
            TODO("Not yet implemented")
        }

        override fun onDismiss() {

        }
    }
