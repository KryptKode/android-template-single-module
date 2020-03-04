package com.kryptkode.template.app.data.remote.api

import com.kryptkode.template.app.data.remote.response.CardRemote
import com.kryptkode.template.app.data.remote.response.CategoryRemote
import com.kryptkode.template.app.data.remote.response.LinkRemote
import com.kryptkode.template.app.data.remote.response.SubCategoryRemote
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by kryptkode on 10/23/2019.
 */

interface Api {

    @GET("categories")
    suspend fun getAllCategories(): List<CategoryRemote>

    @GET("subcategories")
    suspend fun getAllSubCategories(): List<SubCategoryRemote>

    @GET("cards")
    suspend fun getAllWallpapers(): List<CardRemote>


    @GET("categories/{id}/subcategories")
    suspend fun getAllSubCategoriesInCategory(@Path("id") category: String?): List<SubCategoryRemote>


    @GET("subcategories/{id}")
    suspend fun getAllCardsInSubCategory(@Path("id") subCategoryId: String?): List<CardRemote>

    @GET("link")
    suspend fun getLink(): LinkRemote
}