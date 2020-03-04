package com.kryptkode.template.app.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.model.Link
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.app.data.local.mapper.LocalMappers
import com.kryptkode.template.app.data.local.prefs.AppPrefs
import com.kryptkode.template.app.data.local.room.AppDb
import com.kryptkode.template.app.utils.Constants.CARD_CACHE_TIME_MILLIS
import timber.log.Timber
import java.util.*

/**
 * Created by kryptkode on 3/2/2020.
 */
class LocalImpl(
    private val appDb: AppDb,
    private val prefs: AppPrefs,
    private val mappers: LocalMappers
) : Local {

    override fun getAllCategories(): LiveData<List<Category>> {
        return appDb.categoryDao().getAllCategories().map {
            it.map {
                mappers.category.mapFrom(it)
            }
        }
    }

    override fun getFavoriteCategories(): LiveData<List<Category>> {
        return appDb.categoryDao().getAllFavoriteCategories().map {
            it.map {
                mappers.category.mapFrom(it)
            }
        }
    }

    override suspend fun addCategories(list: List<Category>) {
        appDb.categoryDao().upsert(list.map { mappers.category.mapTo(it) })
    }

    override suspend fun updateCategory(card: Category) {
        val newCategory = mappers.category.mapTo(card)
        Timber.d("Update category: $newCategory")
        return appDb.categoryDao().update(newCategory)
    }

    override suspend fun addSubcategories(list: List<SubCategory>) {
        appDb.subCategoryDao().upsert(list.map { mappers.subcategory.mapTo(it) })
    }

    override fun getSubCategoriesInCategory(categoryId: String): LiveData<List<SubCategory>> {
        return appDb.subCategoryDao().getSubCategoriesInCategory(categoryId).map {
            it.map {
                mappers.subcategory.mapFrom(it)
            }
        }
    }

    override suspend fun addCards(list: List<Card>) {
        appDb.cardDao().insert(list.map { mappers.card.mapTo(it) })
    }

    override suspend fun updateCard(card: Card) {
        return appDb.cardDao().update(mappers.card.mapTo(card))
    }

    override fun getCardsInSubCategory(subCategoryId: String): LiveData<List<Card>> {
        return appDb.cardDao().getCardEntitysWithSubCategory(subCategoryId).map {
            it.map {
                mappers.card.mapFrom(it)
            }
        }
    }

    override fun getFavoriteCards(): LiveData<List<Card>> {
        return appDb.cardDao().getAllFavoriteCards().map {
            it.map {
                mappers.card.mapFrom(it)
            }
        }
    }


    override suspend fun saveLink(link: Link) {
        return prefs.setLink(mappers.link.mapTo(link))
    }

    override suspend fun getLink(): Link {
        return mappers.link.mapFrom(prefs.getLink())
    }

    override suspend fun setCardCacheTime(time: Long) {
        return prefs.setCardCacheTime(time)
    }

    override suspend fun isCardCacheExpired(): Boolean {
        return Date().time - prefs.getCardCacheTime() >= CARD_CACHE_TIME_MILLIS
    }

    override suspend fun setLinkCacheTime(time: Long) {
        return prefs.setLinkCacheTime(time)
    }

    override suspend fun isLinkCacheExpired(): Boolean {
        return Date().time - prefs.getLinkCacheTime() >= CARD_CACHE_TIME_MILLIS
    }
}