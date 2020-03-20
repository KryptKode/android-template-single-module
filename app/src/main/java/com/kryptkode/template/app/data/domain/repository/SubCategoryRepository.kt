package com.kryptkode.template.app.data.domain.repository

import androidx.lifecycle.LiveData
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.app.data.domain.state.DataState

/**
 * Created by kryptkode on 2/19/2020.
 */
interface SubCategoryRepository {
    fun getAllSubCategoriesUnderCategory(categoryId:String): LiveData<DataState<List<SubCategory>>>
    fun getFavoriteSubcategories():LiveData<DataState<List<SubCategory>>>
    suspend fun markSubcategoryAsFavorite(subCategory: SubCategory)
    suspend fun unMarkSubcategoryAsFavorite(subCategory: SubCategory)
}