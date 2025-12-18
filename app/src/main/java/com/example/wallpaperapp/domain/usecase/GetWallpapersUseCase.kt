package com.example.wallpaperapp.domain.usecase

import com.example.wallpaperapp.domain.model.WallPaper
import com.example.wallpaperapp.domain.repository.WallPaperRepository
import javax.inject.Inject

class GetWallpapersUseCase @Inject constructor(
    private val repository: WallPaperRepository
) {
    suspend operator fun invoke(query: String): List<WallPaper> {
        return repository.searchWallpaper(query)
    }
}
