package com.kryptkode.template.app.data.local.room.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Created by kryptkode on 3/5/2020.
 */
data class CategoryWithSubCategoriesEntity(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = CategoryEntity.COLUMN_ID,
        entityColumn = SubCategoryEntity.COLUMN_CATEGORY_ID
    ) val subcategories: List<SubCategoryEntity>
)