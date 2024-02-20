package com.example.challengeapp.main.ui.shared

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeapp.R
import com.example.challengeapp.databinding.FragmentSharedBinding
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.ui.adapter.NewsAdapter
import com.example.challengeapp.main.ui.main.MainActivity
import com.example.challengeapp.main.ui.main.MainViewModel
import com.example.challengeapp.main.ui.modal.NewsModalFragment
import com.example.challengeapp.main.ui.modal.ProgressDialogFragment
import com.example.challengeapp.main.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SharedFragment : Fragment(),
    NewsAdapter.OnNewsClickListener,
    NewsModalFragment.OnArticleClickListener,
    AdapterView.OnItemClickListener
    {

        private var _binding: FragmentSharedBinding? = null
        private val mainViewModel by activityViewModels<MainViewModel>()

        private val binding get() = _binding!!
        private lateinit var newsModalFragment: NewsModalFragment
        private lateinit var progressDialogFragment: ProgressDialogFragment
        private var period = 7

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
            initArray()
        }

        private fun setUp(){
            mainViewModel.subtitle.postValue(getString(R.string.most_shared))
        }

        private fun setObservers(){
            progressDialogFragment = ProgressDialogFragment()
            val newProgress = progressDialogFragment.newInstance()

            mainViewModel.fetchMostPopular(Constants.SHARED, period.toString()).observe(viewLifecycleOwner) { result ->
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
            val favorite = validateFav(news)
            news.favorite = favorite
            newsModalFragment = NewsModalFragment(news, this)
            val newInst = newsModalFragment.newInstance(news)
            newInst.show(activity?.supportFragmentManager!!, "newsmodal")
        }

        private fun validateFav(news : News): Boolean{
            try {
                mainViewModel.favList.value?.let {
                    for(i in 0 until mainViewModel.favList.value!!.size){
                        if (mainViewModel.favList.value!![i].id == news.id){
                            news.favorite = true
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("SharedFragment", "Error: ${e.message}")
            }
            return news.favorite
        }

        override fun onLikeClick(news: News) {
            if (!news.favorite){
                mainViewModel.addedToFavorite(news)
            }
            else {
                mainViewModel.deleteFavorite(news)
            }
        }

        override fun onDismiss() {
            (activity as MainActivity).setUpFav()
        }

        private fun initArray(){

            binding.listPeriod.setText(getString(R.string.last_week))

            val period = resources.getStringArray(R.array.period)

            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.list_item,
                period
            )

            with(binding.listPeriod) {
                setAdapter(adapter)
                onItemClickListener=this@SharedFragment
            }
        }

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            period = when(position){
                0 -> 1

                1 -> 7

                2 -> 30

                else -> 7
            }

            setObservers()

        }

    }
