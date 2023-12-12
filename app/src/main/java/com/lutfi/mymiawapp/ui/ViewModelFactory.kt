package com.lutfi.mymiawapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lutfi.mymiawapp.data.CatRepository
import com.lutfi.mymiawapp.ui.screen.detail.DetailViewModel
import com.lutfi.mymiawapp.ui.screen.favorite.FavoriteViewModel
import com.lutfi.mymiawapp.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: CatRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}