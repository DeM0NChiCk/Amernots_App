package com.example.amernotsapp.ui.recyclers.profile

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.amernotsapp.R
import com.example.amernotsapp.databinding.ItemNewslineBinding
import com.example.amernotsapp.ui.model.response.ProfileDataModel

class ProfileHolder(
    private val binding: ItemNewslineBinding,
    private val navController: NavController
) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(
        profileDataModel: ProfileDataModel,
        position: Int,
    ) {
        binding.apply {
            when (profileDataModel.newslineUser[position].urgencyCode) {
                5 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.ic_circle_red_code_5)
                }

                4 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.ic_circle_purple_code_4)
                }

                3 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.ic_circle_turquoise_code_3)
                }

                2 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.ic_circle_dark_green_code_2)
                }

                1 -> {
                    itemProfileImageCodeStatusSituation.setImageResource(R.drawable.ic_circle_green_code_1)
                }
            }

            if (profileDataModel.newslineUser[position].roleNews == profileDataModel.userStatus) {
                itemProfileTvTitleNews.text =
                    profileDataModel.newslineUser[position].tittleSituation
                itemProfileTvStatusNews.text = "ПРИНЯТ"
                itemProfileTvTimeNews.text = profileDataModel.newslineUser[position].timeRelease
                itemProfileTvAddressNews.text = profileDataModel.newslineUser[position].address
            } else {
                itemProfileTvTitleNews.text =
                    profileDataModel.newslineUser[position].tittleSituation

                if (profileDataModel.newslineUser[position].employeeId == -1L) {
                    itemProfileTvStatusNews.text = "НЕ ПРИНЯТ"
                } else {
                    itemProfileTvStatusNews.text = "ПРИНЯТ"
                }
                itemProfileTvTimeNews.text = profileDataModel.newslineUser[position].timeRelease
                itemProfileTvAddressNews.text = profileDataModel.newslineUser[position].address
            }

            root.setOnClickListener {
                navController.navigate(
                    R.id.action_profileFragment_to_additionalInformationNewsFragment,
                    bundleOf("newsId" to profileDataModel.newslineUser[position].newslineId.toString()),
                )
            }
        }

    }
}