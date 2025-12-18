package com.example.wallpaperapp.domain.repository

import com.example.wallpaperapp.data.local.FavoriteEntity
import com.example.wallpaperapp.domain.model.WallPaper

interface WallPaperRepository {
    suspend fun searchWallpaper(query: String): List<WallPaper>
    suspend fun addFavorite(id: Int, imageUrl: String, description: String)
    suspend fun removeFavorite(id: Int, imageUrl: String, description: String)
    suspend fun isFavorite(id: Int): Boolean

    suspend fun getAllFavorite(): List<FavoriteEntity>


}
