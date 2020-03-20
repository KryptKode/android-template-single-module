package com.kryptkode.template.app.data.domain.repository

import androidx.lifecycle.LiveData
import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.domain.state.DataState

/**
 * Created by kryptkode on 2/19/2020.
 */
interface CardRepository {

    fun getCardsForSubcategory(subCategoryId: String): LiveData<DataState<List<Card>>>

    suspend fun refreshCards(subCategoryId: String)

    suspend fun markCardAsFavorite(card: Card)

    suspend fun unMarkCardAsFavorite(card: Card)

    fun getFavoriteCards():LiveData<DataState<List<Card>>>

}