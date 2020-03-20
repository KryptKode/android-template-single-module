package com.kryptkode.template.app.data.domain.repository

import androidx.lifecycle.LiveData
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.model.CategoryWithSubCategories
import com.kryptkode.template.app.data.domain.state.DataState

/**
 * Created by kryptkode on 2/19/2020.
 */
interface CategoryRepository {
    fun getAllCategories(): LiveData<DataState<List<Category>>>
    fun getCategoryWithSubcategories(): LiveData<DataState<List<CategoryWithSubCategories>>>
    suspend fun refreshAllCategoriesAndSubCategories()
    fun getFavoriteCategories(): LiveData<DataState<List<Category>>>
    suspend fun markCategoryAsFavorite(category: Category)
    suspend fun unMarkCategoryAsFavorite(category: Category)
    suspend fun getCategory(categoryId: String): Category
}