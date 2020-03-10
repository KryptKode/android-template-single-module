package com.kryptkode.template.app.data.local


import androidx.lifecycle.LiveData
import com.kryptkode.template.app.data.domain.model.*

/**
 * Created by kryptkode on 2/19/2020.
 */
interface Local {
    fun getAllCategories(): LiveData<List<Category>>
    fun getCategoryWithSubcategories(): LiveData<List<CategoryWithSubCategories>>
    fun getFavoriteCategories(): LiveData<List<Category>>
    suspend fun updateCategory(card: Category)
    suspend fun addCategories(list: List<Category>)

    fun getSubCategoriesInCategory(categoryId: String): LiveData<List<SubCategory>>
    suspend fun addSubcategories(list: List<SubCategory>)

    fun getCardsInSubCategory(subCategoryId: String): LiveData<List<Card>>
    fun getFavoriteCards(): LiveData<List<Card>>
    suspend fun addCards(list: List<Card>)
    suspend fun updateCard(card: Card)
    suspend fun setCardCacheTime(time:Long)
    suspend fun isCardCacheExpired():Boolean

    suspend fun saveLink(link: Link)
    suspend fun getLink():Link
    suspend fun setLinkCacheTime(time:Long)
    suspend fun isLinkCacheExpired():Boolean

}