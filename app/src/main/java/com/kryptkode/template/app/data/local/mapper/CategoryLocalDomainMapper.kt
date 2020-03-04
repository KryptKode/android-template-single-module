package com.kryptkode.template.app.data.local.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.local.room.model.CategoryEntity

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoryLocalDomainMapper : Mapper<CategoryEntity, Category> {

    override fun mapFrom(model: CategoryEntity): Category {
        return Category(model.id, model.name, model.imageUrl, model.status, model.sortOrder, model.favorite, model.locked)
    }

    override fun mapTo(model: Category): CategoryEntity {
        return CategoryEntity(model.id, model.name, model.imageUrl, model.status, model.sortOrder,  model.favorite, model.locked)
    }
}