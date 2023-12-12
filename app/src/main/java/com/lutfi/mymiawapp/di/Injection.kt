package com.lutfi.mymiawapp.di

import com.lutfi.mymiawapp.data.CatRepository

object Injection {
    fun provideRepository(): CatRepository {
        return CatRepository.getInstance()
    }
}