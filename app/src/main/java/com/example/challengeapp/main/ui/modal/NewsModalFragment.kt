package com.example.challengeapp.main.ui.modal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.challengeapp.databinding.FragmentNewsModalBinding
import com.example.challengeapp.main.data.model.News
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewsModalFragment(
    private val news: News,
    private val onArticleClickListener: OnArticleClickListener
    ) : BottomSheetDialogFragment() {

    private var _binding: FragmentNewsModalBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressDialogFragment: ProgressDialogFragment

    interface OnArticleClickListener  {
        fun onLikeClick(news: News)
        fun onDismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsModalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            onArticleClickListener.onDismiss()
            dismiss()
        }

        try {
            progressDialogFragment = ProgressDialogFragment()
            val newProgress = progressDialogFragment.newInstance()
            newProgress.show(activity?.supportFragmentManager!!, "progress dialog")

            binding.webView.loadUrl(news.url)

            binding.webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    newProgress.dismiss()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.tvTitle.text = news.title

        binding.buttonFav.isActivated = news.favorite

        binding.buttonFav.setOnClickListener {
            binding.buttonFav.isActivated = !binding.buttonFav.isActivated
            onArticleClickListener.onLikeClick(news)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        onArticleClickListener.onDismiss()
    }

    fun newInstance(news: News): NewsModalFragment {
        val frag = NewsModalFragment(news, onArticleClickListener)
        val args = Bundle()
        frag.arguments = args
        return frag
    }



}