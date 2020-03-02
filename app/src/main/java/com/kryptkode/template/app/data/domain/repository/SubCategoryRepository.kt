package com.kryptkode.template.app.data.domain.repository

import com.kryptkode.template.app.data.domain.model.SubCategory

/**
 * Created by kryptkode on 2/19/2020.
 */
interface SubCategoryRepository {
    suspend fun getAllSubCategoriesUnderCategory(categoryId:String):List<SubCategory>
}