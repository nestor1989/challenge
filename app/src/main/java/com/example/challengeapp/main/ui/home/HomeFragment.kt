package com.example.challengeapp.main.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeapp.R
import com.example.challengeapp.databinding.FragmentHomeBinding
import com.example.challengeapp.main.core.Resource
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.model.NewsDTO
import com.example.challengeapp.main.ui.adapter.NewsAdapter
import com.example.challengeapp.main.ui.main.MainActivity
import com.example.challengeapp.main.ui.main.MainViewModel
import com.example.challengeapp.main.ui.modal.NewsModalFragment
import com.example.challengeapp.main.ui.modal.ProgressDialogFragment
import com.example.challengeapp.main.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(),
                    NewsAdapter.OnNewsClickListener,
                    NewsModalFragment.OnArticleClickListener,
                    AdapterView.OnItemClickListener{

    private var _binding: FragmentHomeBinding? = null
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val binding get() = _binding!!
    private lateinit var newsModalFragment: NewsModalFragment
    private var progressDialogFragment = ProgressDialogFragment()
    private val newProgress = progressDialogFragment.newInstance()
    private var period = 7

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
        initArray()
    }

    private fun setUp(){
        mainViewModel.subtitle.postValue(getString(R.string.most_viewed))
        mainViewModel.fetchFlowMostPopular(Constants.VIEWED, period.toString())
    }

    private fun setObservers(){

        /*mainViewModel.fetchMostPopular(Constants.VIEWED, period.toString()).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    newProgress.show(activity?.supportFragmentManager!!, "progress dialog")
                    binding.prError.visibility = View.GONE
                }

                is Resource.Success -> {
                    initAdapter(result.data)
                    Log.d("LIST_DATA", "Data: ${result.data}")
                    newProgress.dismiss()
                    binding.prError.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                }

                is Resource.Failure -> {
                    Log.d("HomeFragment", "Error: ${result.exception}")
                    newProgress.dismiss()
                    binding.prError.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        }*/

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.uiState.collect{ uiState ->
                    when (uiState) {
                        is Resource.Loading -> {
                            newProgress.show(activity?.supportFragmentManager!!, "progress dialog")
                            binding.prError.visibility = View.GONE
                        }

                        is Resource.Success -> {
                            initAdapter(uiState.data)
                            Log.d("LIST_DATA_FLOW", "Data: ${uiState.data}")
                            newProgress.dismiss()
                            binding.prError.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                        }

                        is Resource.Failure -> {
                            Log.d("HomeFragment", "Error: ${uiState.exception}")
                            newProgress.dismiss()
                            binding.prError.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                        }
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNewsClick(news: NewsDTO) {
       modalInst(news)
    }

    private fun initAdapter(list : List<NewsDTO>){
        val appContext = requireContext()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager= LinearLayoutManager(appContext)
        binding.recyclerView.adapter= NewsAdapter(requireContext(), list, this)
    }

    private fun modalInst(news: NewsDTO){
        val favorite = validateFav(news)
        news.favorite = favorite
        newsModalFragment = NewsModalFragment(news, this)
        val newInst = newsModalFragment.newInstance(news)
        newInst.show(activity?.supportFragmentManager!!, "newsmodal")
    }

    private fun validateFav(news : NewsDTO): Boolean{
        try {
            mainViewModel.favList.value.let {
                for(i in 0 until mainViewModel.favList.value!!.size){
                    if (mainViewModel.favList.value!![i].id == news.id){
                        news.favorite = true
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("HomeFragment", "Error: ${e.message}")
        }
        return news.favorite
    }

    override fun onLikeClick(news: NewsDTO) {
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
            onItemClickListener=this@HomeFragment
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        period = when(position){
            0 -> 1

            1 -> 7

            2 -> 30

            else -> 7
        }
        mainViewModel.fetchFlowMostPopular(Constants.VIEWED, period.toString())
    }

    override fun onResume() {
        super.onResume()
        initArray()
    }


}