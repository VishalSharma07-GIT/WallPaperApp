package com.example.wallpaperapp.ui.home


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wallpaperapp.domain.model.WallPaper
import com.example.wallpaperapp.domain.usecase.GetWallpapersUseCase
import com.example.wallpaperapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWallpapersUseCase: GetWallpapersUseCase
) : ViewModel() {

    private val _wallpaperState = MutableStateFlow<UiState<List<WallPaper>>>(UiState.Loading)
    val wallpaperState: StateFlow<UiState<List<WallPaper>>>  = _wallpaperState

    init{
        loadWallpapers()
    }

    fun loadWallpapers(){
        viewModelScope.launch {
            _wallpaperState.value = UiState.Loading
            try{
                val result= getWallpapersUseCase("")
                _wallpaperState.value= UiState.Success(result)
            } catch (e: Exception){
                _wallpaperState.value= UiState.Error(e.localizedMessage ?: "Something Went Wrong")
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _wallpaperState.value = UiState.Loading
            try{
                val result = getWallpapersUseCase(query)
                _wallpaperState.value= UiState.Success(result)

            }catch (e: Exception){
                _wallpaperState.value= UiState.Error(e.localizedMessage?: "Unable to fetch the Wall Paper")
            }
        }
    }
}