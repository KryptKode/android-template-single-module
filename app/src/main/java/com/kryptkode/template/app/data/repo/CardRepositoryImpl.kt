package com.kryptkode.template.app.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.remote.Remote
import com.kryptkode.template.app.utils.DateHelper
import kotlinx.coroutines.withContext

/**
 * Created by kryptkode on 3/12/2020.
 */
class CardRepositoryImpl(
    private val dispatcher: AppDispatchers,
    private val dateHelper: DateHelper,
    private val local: Local,
    private val remote: Remote
) : CardRepository {

    override fun getCardsForSubcategory(subCategoryId: String) = liveData {
        val cachedExpired = local.isCardCacheExpired(subCategoryId)
        if (cachedExpired) {
            refreshCards(subCategoryId)
            local.setCardCacheTime(subCategoryId, dateHelper.nowInMillis())
        }
        emitSource(local.getCardsInSubCategory(subCategoryId))
    }

    override suspend fun refreshCards(subCategoryId: String) {
        return withContext(dispatcher.network) {
            val cardsResult = remote.getAllCardsInSubCategory(subCategoryId)
            local.addCards(cardsResult)
        }
    }

    override suspend fun markCardAsFavorite(card: Card) {
        return local.updateCard(card.copy(favorite = true))
    }

    override suspend fun unMarkCardAsFavorite(card: Card) {
        return local.updateCard(card.copy(favorite = false))
    }

    override fun getFavoriteCards(): LiveData<List<Card>> {
        return local.getFavoriteCards()
    }
}