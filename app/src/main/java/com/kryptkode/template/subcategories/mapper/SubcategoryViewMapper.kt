package com.kryptkode.template.subcategories.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/8/2020.
 */
class SubcategoryViewMapper : Mapper<SubCategoryForView, SubCategory> {
    override fun mapFrom(model: SubCategoryForView): SubCategory {
        return SubCategory(
            model.id,
            model.name,
            model.parentId,
            model.imageUrl,
            model.status,
            model.sortOrder,
            model.favorite
        )
    }

    override fun mapTo(model: SubCategory): SubCategoryForView {
        return SubCategoryForView(
            model.id,
            model.name,
            model.parentId,
            model.imageUrl,
            model.status,
            model.sortOrder,
            model.favorite
        )
    }
}