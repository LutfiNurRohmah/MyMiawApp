package com.lutfi.mymiawapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lutfi.mymiawapp.common.UiState
import com.lutfi.mymiawapp.data.CatRepository
import com.lutfi.mymiawapp.model.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: CatRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Cat>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Cat>>>
        get() = _uiState

    fun getAllCats() {
        viewModelScope.launch {
            repository.getAllCats()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { cats ->
                    _uiState.value = UiState.Success(cats)
                }
        }
    }
}