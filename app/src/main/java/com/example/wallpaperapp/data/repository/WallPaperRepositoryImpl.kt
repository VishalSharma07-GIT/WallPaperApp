package com.example.wallpaperapp.data.repository

import com.example.wallpaperapp.data.remote.ApiService
import com.example.wallpaperapp.domain.model.WallPaper
import com.example.wallpaperapp.domain.repository.WallPaperRepository
import javax.inject.Inject
import android.util.Log
import com.example.wallpaperapp.data.local.FavoriteDao
import com.example.wallpaperapp.data.local.FavoriteEntity


class WallPaperRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val favoriteDao: FavoriteDao
)  : WallPaperRepository{

    override suspend fun searchWallpaper(query: String): List<WallPaper> {

        val finalQuery = query.ifEmpty { "trending" }

        Log.d("API_TEST", "Calling API for query: $query")


        val response = api.searchPhotos(finalQuery )

        Log.d("API_RESPONSE", "response = ${response.body()}")

        return if (response.isSuccessful){
            response.body()?.photos?.map {
                WallPaper(                                      //hERE WE CONVERT DTO FEILDS INTO CLEARN DOMAIN MODEL
                    id=it.id,                                   //UI NEVER USE API MODEL ONLY DOMAIN MODEL
                    photographer = it.photographer?:" ",        //
                    imageUrl = it.src.large2x?: "",
                    description = it.alt ?:"no description available "
                )
            }?: emptyList()
        }else emptyList()

    }

    override suspend fun addFavorite(id: Int, imageUrl: String, description: String) {
        favoriteDao.addFavorite(
            FavoriteEntity(id, imageUrl,description)
        )

    }

    override suspend fun removeFavorite(id: Int, imageUrl: String, description: String) {
    favoriteDao.removeFavorite(
        FavoriteEntity(id, imageUrl,description)
    )

    }

    override suspend fun isFavorite(id: Int): Boolean {

        return favoriteDao.isFavorite(id)

    }

    override suspend fun getAllFavorite(): List<FavoriteEntity> {

        return favoriteDao.getAllFavorites()
    }


}
