package com.example.wallpaperapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.domain.repository.WallPaperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: WallPaperRepository
) : ViewModel(){
    suspend fun isFavorite(id: Int): Boolean{
        return repository.isFavorite(id)
    }
    fun addFavorite(id: Int, imageUrl: String,description: String){
        viewModelScope.launch {
            repository.addFavorite(id, imageUrl,description)
        }
    }
    fun removeFavorite(id: Int, imageUrl: String, description: String){
        viewModelScope.launch {
            repository.removeFavorite(id, imageUrl,description)
        }
    }

}