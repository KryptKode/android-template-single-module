package com.kryptkode.template.app.data.domain.repository

import androidx.lifecycle.LiveData
import com.kryptkode.template.app.data.domain.model.SubCategory

/**
 * Created by kryptkode on 2/19/2020.
 */
interface SubCategoryRepository {
    fun getAllSubCategoriesUnderCategory(categoryId:String): LiveData<List<SubCategory>>
    fun getFavoriteSubcategories():LiveData<List<SubCategory>>
    suspend fun markSubcategoryAsFavorite(subCategory: SubCategory)
    suspend fun unMarkSubcategoryAsFavorite(subCategory: SubCategory)
}