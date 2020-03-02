package com.kryptkode.template.app.data.remote

import com.kryptkode.template.app.data.domain.model.Card
import com.kryptkode.template.app.data.domain.model.Link
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.app.data.remote.response.CategoryRemote
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by kryptkode on 10/23/2019.
 */

interface Api {

    @GET("categories")
    suspend fun getAllCategories(): List<CategoryRemote>

    @GET("subcategories")
    suspend fun getAllSubCategories(): List<SubCategory>

    @GET("cards")
    suspend fun getAllWallpapers(): List<Card>


    @GET("categories/{id}/subcategories")
    suspend fun getAllSubCategoriesInCategory(@Path("id") category: String?): List<SubCategory>


    @GET("subcategories/{id}")
    suspend fun getAllCardsInSubCategory(@Path("id") subCategoryId: String?): List<Card>

    @GET("link")
    suspend fun getLink(): Link
}