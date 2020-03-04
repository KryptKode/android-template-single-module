package com.kryptkode.template.app.data.domain.repository

import androidx.lifecycle.LiveData
import com.kryptkode.template.app.data.domain.model.Category

/**
 * Created by kryptkode on 2/19/2020.
 */
interface CategoryRepository {
    fun getAllCategories(): LiveData<List<Category>>
    suspend fun refreshAllCategories()
    fun getFavoriteCategories(): LiveData<List<Category>>
    suspend fun markCardAsFavorite(category: Category)
    suspend fun unMarkCardAsFavorite(category: Category)
}