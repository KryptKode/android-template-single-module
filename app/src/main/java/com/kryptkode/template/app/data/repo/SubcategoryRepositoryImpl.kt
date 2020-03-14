package com.kryptkode.template.app.data.repo

import androidx.lifecycle.LiveData
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.app.data.domain.repository.SubCategoryRepository
import com.kryptkode.template.app.data.local.Local

/**
 * Created by kryptkode on 3/12/2020.
 */
class SubcategoryRepositoryImpl(
    private val local: Local
) : SubCategoryRepository {

    override fun getAllSubCategoriesUnderCategory(categoryId: String): LiveData<List<SubCategory>> {
        return local.getSubCategoriesInCategory(categoryId)
    }

    override fun getFavoriteSubcategories(): LiveData<List<SubCategory>> {
        return  local.getFavoriteSubcategories()
    }

    override suspend fun markSubcategoryAsFavorite(subCategory: SubCategory) {
        return local.updateSubcategory(subCategory.copy(favorite = true))
    }

    override suspend fun unMarkSubcategoryAsFavorite(subCategory: SubCategory) {
        return local.updateSubcategory(subCategory.copy(favorite = true))
    }
}