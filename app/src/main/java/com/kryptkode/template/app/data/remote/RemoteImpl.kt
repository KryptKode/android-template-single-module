package com.kryptkode.template.app.data.remote

import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.model.Link
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.app.data.remote.api.Api
import com.kryptkode.template.app.data.remote.mapper.RemoteMappers

/**
 * Created by kryptkode on 3/3/2020.
 */
class RemoteImpl(
    private val api: Api,
    private val remoteMappers: RemoteMappers
) : Remote {
    override suspend fun getAllCategories(): List<Category> {
        return api.getAllCategories().map { remoteMappers.category.mapFrom(it) }
    }

    override suspend fun getAllSubCategories(): List<SubCategory> {
        return api.getAllSubCategories().map { remoteMappers.subcategory.mapFrom(it) }
    }

    override suspend fun getAllWallpapers(): List<Card> {
        return api.getAllWallpapers().map { remoteMappers.card.mapFrom(it) }
    }

    override suspend fun getAllSubCategoriesInCategory(category: String?): List<SubCategory> {
        return api.getAllSubCategoriesInCategory(category)
            .map { remoteMappers.subcategory.mapFrom(it) }
    }

    override suspend fun getAllCardsInSubCategory(subCategoryId: String?): List<Card> {
        return api.getAllCardsInSubCategory(subCategoryId).map { remoteMappers.card.mapFrom(it) }
    }

    override suspend fun getLink(): Link {
        return remoteMappers.link.mapFrom(api.getLink())
    }
}