package com.example.wallpaperapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.data.local.FavoriteEntity
import com.example.wallpaperapp.domain.repository.WallPaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: WallPaperRepository
): ViewModel() {
    private val _favorites= MutableStateFlow<List<FavoriteEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteEntity>> =_favorites

    fun loadFavorites(){
        viewModelScope.launch {
            _favorites.value=repository.getAllFavorite()
        }
    }
}