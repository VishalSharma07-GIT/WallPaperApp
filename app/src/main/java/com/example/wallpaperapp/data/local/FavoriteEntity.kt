package com.example.wallpaperapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites")
class FavoriteEntity(
    @PrimaryKey val id: Int,
    val imageUrl: String,
    val description: String



) {
}