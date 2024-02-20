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

class NewsAdapter(private val context: Context, private val newsList:List<News>,
                  private val itemClickListener:OnNewsClickListener):
    RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnNewsClickListener{
        fun onNewsClick(news: News)
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
        BaseViewHolder<News>(itemBinding.root) {
        override fun bind(item: News) {
            try {
                val image = item.media[0].mediaMetadata[0].url
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