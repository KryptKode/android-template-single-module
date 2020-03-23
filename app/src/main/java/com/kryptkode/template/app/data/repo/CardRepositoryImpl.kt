package com.kryptkode.template.app.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.domain.error.ErrorHandler
import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.domain.repository.CardRepository
import com.kryptkode.template.app.data.domain.state.DataState
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.remote.Remote
import com.kryptkode.template.app.utils.extensions.handleError
import kotlinx.coroutines.withContext

/**
 * Created by kryptkode on 3/12/2020.
 */
class CardRepositoryImpl(
    private val dispatcher: AppDispatchers,
    private val local: Local,
    private val remote: Remote,
    private val errorHandler: ErrorHandler
) : CardRepository {

    override fun getCardsForSubcategory(subCategoryId: String): LiveData<DataState<List<Card>>> {
        return liveData {
            try {
                emit(DataState.Loading)
                val cachedExpired = local.isCardCacheExpired(subCategoryId)
                if (cachedExpired) {
                    refreshCards(subCategoryId)
                    local.updateCardCacheTime(subCategoryId)
                }
                val result : LiveData<DataState<List<Card>>> = local.getCardsInSubCategory(subCategoryId)
                    .map { DataState.Success(it) }
                emitSource(result)
            }catch (e:Exception){
                handleError<List<Card>>(errorHandler, e)
            }
        }
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

    override fun getFavoriteCards(): LiveData<DataState<List<Card>>> {
        return liveData {
            try {
                emit(DataState.Loading)
                val result : LiveData<DataState<List<Card>>> = local.getFavoriteCards().map {
                    DataState.Success(it)
                }
                emitSource(result)
            }catch (e:Exception){
                handleError<List<Card>>(errorHandler, e)
            }
        }
    }
}