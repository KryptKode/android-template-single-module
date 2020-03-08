package com.kryptkode.template.app.data.domain.model


/**
 * Created by kryptkode on 3/5/2020.
 */
data class CategoryWithSubCategories(
    val category: Category,
    val subcategories: List<SubCategory>
)