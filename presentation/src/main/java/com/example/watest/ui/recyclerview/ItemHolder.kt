package com.example.watest.ui.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Landmark
import com.example.watest.R
import com.example.watest.databinding.ItemLandmarkBinding

class ItemHolder(
    val context: Context,
    private val binding: ItemLandmarkBinding,
    private val clickLambda: (Int) -> Unit,
    private val likeLambda: (Int, ImageView) -> Unit
) : RecyclerView.ViewHolder(binding.root) {


    fun bind(landmark: Landmark) {
        setLikeIcon(landmark.isFavorite)
        setItemIcon(landmark.imageName)
        setupLikeListener(landmark)
        binding.tvName.text = landmark.name
        itemView.setOnClickListener {
            clickLambda(landmark.id)
        }
    }

    private fun setupLikeListener(landmark: Landmark) {
        binding.ivLike.setOnClickListener {
            val isFav = landmark.isFavorite
            landmark.isFavorite = !isFav
            likeLambda(landmark.id, it as ImageView)
        }
    }

    private fun setItemIcon(imageName: String) {
        var resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        resId = if (resId == 0) R.drawable.placeholder else resId
        binding.ivPoster.setImageResource(resId)
    }

    private fun setLikeIcon(isFavorite: Boolean) {
        if (isFavorite) binding.ivLike.setImageResource(R.drawable.ic_liked)
        else binding.ivLike.setImageResource(R.drawable.ic_disliked)
    }

    companion object {
        fun create(
            context: Context,
            parent: ViewGroup,
            clickLambda: (Int) -> Unit,
            likeLambda: (Int, ImageView) -> Unit,
            ): ItemHolder = ItemHolder(
            context,
            ItemLandmarkBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            clickLambda,
            likeLambda
        )
    }
}