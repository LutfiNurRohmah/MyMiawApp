package com.lutfi.mymiawapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lutfi.mymiawapp.common.UiState
import com.lutfi.mymiawapp.data.CatRepository
import com.lutfi.mymiawapp.model.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: CatRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Cat>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Cat>>
        get() = _uiState

    private val _isFavorite: MutableStateFlow<UiState<Boolean>> =
        MutableStateFlow(UiState.Loading)
    val isFavorite: StateFlow<UiState<Boolean>>
        get() = _isFavorite


    fun getCatById(catId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getCatById(catId))
        }
    }

    fun getFavoriteById(catId: Int) {
        viewModelScope.launch {
            _isFavorite.value = UiState.Loading
            _isFavorite.value = UiState.Success(repository.getFavoriteById(catId))
        }
    }

    fun addToFavorite(cat: Cat) {
        viewModelScope.launch {
            repository.addToFavorite(cat)
        }
    }

    fun deleteFromFavorite(cat: Cat) {
        viewModelScope.launch {
            repository.deleteFromFavorite(cat)
        }
    }
}