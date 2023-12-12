package com.lutfi.mymiawapp.data

import com.lutfi.mymiawapp.model.Cat
import com.lutfi.mymiawapp.model.dummyCats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CatRepository {

    private val listCats = mutableListOf<Cat>()
    private val favoriteCats = mutableListOf<Cat>()

    init {
        if (listCats.isEmpty()) {
            dummyCats.forEach {
                listCats.add(it)
            }
        }

    }

    fun getAllCats(): Flow<List<Cat>> {
        return flowOf(listCats)
    }

    fun getCatById(catId: Int): Cat {
        return listCats.first {
            it.id == catId
        }
    }

    fun addToFavorite(cat: Cat): Flow<Boolean> {
        return flowOf(favoriteCats.add(cat))
    }

    fun deleteFromFavorite(cat: Cat): Flow<Boolean> {
        return flowOf(favoriteCats.remove(cat))
    }

    fun getFavoriteById(catId: Int): Boolean {
        return favoriteCats.any {
            it.id == catId
        }
    }

    fun getAllFavorite(): Flow<List<Cat>> {
        return flowOf(favoriteCats)
    }

    companion object {
        @Volatile
        private var instance: CatRepository? = null

        fun getInstance(): CatRepository =
            instance ?: synchronized(this) {
                CatRepository().apply {
                    instance = this
                }
            }
    }
}