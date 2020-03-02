package com.kryptkode.template.app.data.domain.repository

import androidx.lifecycle.LiveData
import com.kryptkode.template.app.data.domain.model.Card

/**
 * Created by kryptkode on 2/19/2020.
 */
interface CardRepository {

    suspend fun getCardsForSubcategory(subCategoryId: String): LiveData<Card>

    suspend fun markCardAsFavorite(card: Card)

    suspend fun unMarkCardAsFavorite(card: Card)

}