package com.lutfi.mymiawapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lutfi.mymiawapp.common.UiState
import com.lutfi.mymiawapp.data.CatRepository
import com.lutfi.mymiawapp.model.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: CatRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Cat>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Cat>>>
        get() = _uiState

    fun getAllFavoriteCats() {
        viewModelScope.launch {
            repository.getAllFavorite()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { cats ->
                    _uiState.value = UiState.Success(cats)
                }
        }
    }
}