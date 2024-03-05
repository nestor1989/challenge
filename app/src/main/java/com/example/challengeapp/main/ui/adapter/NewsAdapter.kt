package com.example.challengeapp.main.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengeapp.R
import com.example.challengeapp.databinding.RowNewsBinding
import com.example.challengeapp.main.core.BaseViewHolder
import com.example.challengeapp.main.data.model.News
import com.example.challengeapp.main.data.model.NewsDTO

class NewsAdapter(private val context: Context, private val newsList:List<NewsDTO>,
                  private val itemClickListener:OnNewsClickListener):
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnNewsClickListener{
        fun onNewsClick(news: NewsDTO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            RowNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is MainViewHolder->holder.bind(newsList[position])
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class MainViewHolder(private val itemBinding: RowNewsBinding):
        BaseViewHolder<NewsDTO>(itemBinding.root) {
        override fun bind(item: NewsDTO) {
            try {
                val image = item.mediaUrls[0]
                Glide.with(context)
                    .load(image)
                    .centerCrop()
                    .placeholder(R.drawable.nytimes_icon)
                    .dontAnimate()
                    .into(itemBinding.ivPhoto)
            }catch (e:Exception) {
                e.printStackTrace()
            }

            itemBinding.tvTitle.setText(item.title)
            itemBinding.tvDesc.setText(item.abstractField)

            itemView.setOnClickListener {itemClickListener.onNewsClick(item)}
        }
    }
}