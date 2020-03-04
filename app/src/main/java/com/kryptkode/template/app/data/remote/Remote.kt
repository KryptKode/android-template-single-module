package com.kryptkode.template.app.data.remote

import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.model.Link
import com.kryptkode.template.app.data.domain.model.SubCategory


/**
 * Created by kryptkode on 3/2/2020.
 */
interface Remote {
    suspend fun getAllCategories(): List<Category>

    suspend fun getAllSubCategories(): List<SubCategory>

    suspend fun getAllWallpapers(): List<Card>

    suspend fun getAllSubCategoriesInCategory(category: String?): List<SubCategory>

    suspend fun getAllCardsInSubCategory(subCategoryId: String?): List<Card>

    suspend fun getLink(): Link
}