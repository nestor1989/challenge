package com.example.challengeapp.main.ui.favs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeapp.R
import com.example.challengeapp.databinding.FragmentFavsBinding
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
class FavsFragment : Fragment(),
    NewsAdapter.OnNewsClickListener,
    NewsModalFragment.OnArticleClickListener{

    private var _binding: FragmentFavsBinding? = null
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val binding get() = _binding!!
    private lateinit var newsModalFragment: NewsModalFragment
    private lateinit var progressDialogFragment: ProgressDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
        setObservers()

    }

    private fun setUp(){
        mainViewModel.subtitle.postValue(getString(R.string.favs))
    }

    private fun setObservers(){
        progressDialogFragment = ProgressDialogFragment()
        val newProgress = progressDialogFragment.newInstance()

        mainViewModel.getFavorites().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    newProgress.show(activity?.supportFragmentManager!!, "progress")
                }
                is Resource.Success -> {
                    newProgress.dismiss()
                    initAdapter(result.data)
                    if (!result.data.isEmpty()){
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.lytNofavs.visibility = View.GONE
                    }else {
                        binding.lytNofavs.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }

                }
                is Resource.Failure -> {
                    newProgress.dismiss()
                    Log.d("FavsFragment", "Error: ${result.exception}")
                    binding.lytNofavs.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
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
            mainViewModel.favList?.let {
                for(i in 0 until mainViewModel.favList?.value!!.size){
                    if (mainViewModel.favList?.value!![i].id == news.id){
                        news.favorite = true
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("HomeFragment", "Error: ${e.message}")
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
        (activity as MainActivity).setUpFav()
    }

    override fun onDismiss() {
        setObservers()
    }
}