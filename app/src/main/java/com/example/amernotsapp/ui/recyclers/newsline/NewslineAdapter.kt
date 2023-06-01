package com.example.amernotsapp.ui.recyclers.newsline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.amernotsapp.databinding.ItemNewslineBinding
import com.example.amernotsapp.ui.model.response.NewslineDataModel

class NewslineAdapter(
    private val newslineDataModel: NewslineDataModel,
    private val navController: NavController
): RecyclerView.Adapter<NewslineHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewslineHolder =
        NewslineHolder(
            ItemNewslineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            navController
        )

    override fun getItemCount(): Int = newslineDataModel.newsline.size


    override fun onBindViewHolder(holder: NewslineHolder, position: Int) {
        holder.bind(newslineDataModel, position)
    }
}