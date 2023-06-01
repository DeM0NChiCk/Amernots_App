package com.example.amernotsapp.ui.recyclers.newsline

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.amernotsapp.R
import com.example.amernotsapp.databinding.ItemNewslineBinding
import com.example.amernotsapp.ui.model.response.NewslineDataModel

class NewslineHolder(
    private val binding: ItemNewslineBinding,
    private val navController: NavController
): RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(
        newslineDataModel: NewslineDataModel,
        position: Int,
    ) {
        binding.apply {
            when (newslineDataModel.newsline[position].urgencyCode) {
                5 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.baseline_circle_red_24)
                }

                4 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.baseline_circle_purple_24)
                }

                3 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.baseline_circle_turquoise_24)
                }

                2 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.baseline_circle_dark_green_24)
                }
                1 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.baseline_circle_green_24)
                }
            }

            itemProfileTvTitleNews.text =
                newslineDataModel.newsline[position].tittleSituation

            if (newslineDataModel.newsline[position].employeeId == -1L) {
                itemProfileTvStatusNews.text = "НЕ ПРИНЯТ"
            } else {
                itemProfileTvStatusNews.text = "ПРИНЯТ"
            }
            itemProfileTvTimeNews.text = newslineDataModel.newsline[position].timeRelease
            itemProfileTvAddressNews.text = newslineDataModel.newsline[position].address

            root.setOnClickListener {
                navController.navigate(
                    R.id.action_newslineFragment_to_additionalInformationNewsFragment,
                    bundleOf("newsId" to newslineDataModel.newsline[position].newslineId.toString()),
                )
            }

        }
    }
}