package com.kryptkode.template.app.data.local


import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.model.SubCategory
import kotlinx.coroutines.flow.Flow

/**
 * Created by kryptkode on 2/19/2020.
 */
interface Local{
    fun getAllCategories(): List<Category>
    fun getFavoriteCategories(): List<Category>
    suspend fun markCategoryAsFavorite(card: Category)
    suspend fun unMarkCategoryAsFavorite(card: Category)

    fun getSubCategoriesInCategory(categoryId:String): Flow<List<SubCategory>>

    fun getCardsInSubCategory(subCategoryId:String): Flow<List<Card>>
    fun getFavoriteCards(): Flow<List<Card>>

    suspend fun markCardAsFavorite(card: Card)
    suspend fun unMarkCardAsFavorite(card: Category)
}