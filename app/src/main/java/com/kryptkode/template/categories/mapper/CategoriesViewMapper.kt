package com.kryptkode.template.categories.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.categories.model.CategoryForView

/**
 * Created by kryptkode on 3/3/2020.
 */
class CategoriesViewMapper : Mapper<CategoryForView, Category> {
    override fun mapFrom(model: CategoryForView): Category {
        return Category(
            model.id,
            model.name,
            model.imageUrl,
            model.status,
            model.sortOrder,
            model.favorite,
            model.locked
        )
    }

    override fun mapTo(model: Category): CategoryForView {
        return CategoryForView(
            model.id,
            model.name,
            model.imageUrl,
            model.favorite,
            model.status,
            model.sortOrder,
            model.locked
        )
    }
}