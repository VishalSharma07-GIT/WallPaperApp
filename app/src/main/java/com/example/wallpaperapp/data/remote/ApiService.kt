package com.example.wallpaperapp.data.remote

import com.example.wallpaperapp.data.remote.dto.PexelsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("per_page") perPage: Int=20

    ): Response<PexelsResponse>


}