package com.example.amernotsapp.ui.recyclers.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amernotsapp.databinding.ItemNewslineBinding
import com.example.amernotsapp.ui.model.response.ProfileDataModel

class ProfileAdapter(
    private val profileDataModel: ProfileDataModel
): RecyclerView.Adapter<ProfileHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileHolder =
        ProfileHolder(
            ItemNewslineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = profileDataModel.newslineUser.size

    override fun onBindViewHolder(holder: ProfileHolder, position: Int) {
        holder.bind(profileDataModel, position)
    }
}