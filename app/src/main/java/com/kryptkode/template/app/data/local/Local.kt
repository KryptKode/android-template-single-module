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
    suspend fun getCategory(categoryId: String): Category

    suspend fun addCategories(list: List<Category>)
    fun getSubCategoriesInCategory(categoryId: String): LiveData<List<SubCategory>>
    fun getFavoriteSubcategories(): LiveData<List<SubCategory>>
    suspend fun addSubcategories(list: List<SubCategory>)

    suspend fun updateSubcategory(subCategory: SubCategory)
    fun getCardsInSubCategory(subCategoryId: String): LiveData<List<Card>>
    fun getFavoriteCards(): LiveData<List<Card>>
    suspend fun addCards(list: List<Card>)
    suspend fun updateCard(card: Card)
    suspend fun setCardCacheTime(subcategoryId: String, time: Long)
    suspend fun setCategoryCacheTime(time: Long)

    suspend fun isCardCacheExpired(subcategoryId: String): Boolean
    suspend fun isCategoryCacheExpired(): Boolean
    suspend fun saveLink(link: Link)
    suspend fun getLink(): Link
    suspend fun setLinkCacheTime(time: Long)
    suspend fun isLinkCacheExpired(): Boolean

    fun isCategoryLocked(categoryId:String): Boolean
    fun setCategoryLocked(categoryId:String, value: Boolean)
    fun setDateWhenCategoryWasUnlocked(categoryId: String, time: Long)
    fun getDateWhenCategoryWasUnlocked(categoryId: String):Long

}