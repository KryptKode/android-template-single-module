package com.kryptkode.template.app.data.domain.repository

import com.kryptkode.template.app.data.domain.model.Category

/**
 * Created by kryptkode on 2/19/2020.
 */
interface CategoryRepository {
    suspend fun getAllCategories(): List<Category>
    suspend fun getFavoriteCategories(): List<Category>
    suspend fun markCardAsFavorite(card: Category)
    suspend fun unMarkCardAsFavorite(card: Category)
}