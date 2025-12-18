package com.example.wallpaperapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wallpaperapp.R
import com.example.wallpaperapp.domain.model.WallPaper

class WallpaperAdapter(
    private val onItemClick: (WallPaper) -> Unit,
    private val isFavorite: (Int) -> Boolean
) : ListAdapter<WallPaper, WallpaperAdapter.WallpaperViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallpaper, parent, false)
        return WallpaperViewHolder(view)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WallpaperViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imgWallpaper: ImageView =
            itemView.findViewById(R.id.imgWallpaper)

        private val btnFavoriteIcon: ImageView =
            itemView.findViewById(R.id.btnFavoriteIcon)

        fun bind(wallpaper: WallPaper) {

            imgWallpaper.load(wallpaper.imageUrl)

            val icon = if (isFavorite(wallpaper.id)) {
                R.drawable.ic_favorite_filled
            } else {
                R.drawable.ic_favorite_border
            }

            btnFavoriteIcon.setImageResource(icon)

            itemView.setOnClickListener {
                onItemClick(wallpaper)
            }
        }
    }

    // ✅ DiffCallback MUST be here
    class DiffCallback : DiffUtil.ItemCallback<WallPaper>() {
        override fun areItemsTheSame(oldItem: WallPaper, newItem: WallPaper): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WallPaper, newItem: WallPaper): Boolean {
            return oldItem == newItem
        }
    }
}
