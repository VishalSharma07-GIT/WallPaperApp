package com.example.wallpaperapp.ui.favorites

import com.example.wallpaperapp.data.local.FavoriteEntity
import com.example.wallpaperapp.domain.model.WallPaper

fun FavoriteEntity.toWallPaper(): WallPaper {
    return WallPaper(
        id = id,
        imageUrl = imageUrl,
        photographer = "",
        description = description
    )
}
