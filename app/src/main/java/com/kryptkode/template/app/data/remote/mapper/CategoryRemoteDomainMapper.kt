package com.kryptkode.template.app.data.remote.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.remote.response.CategoryRemote

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoryRemoteDomainMapper : Mapper<CategoryRemote, Category> {

    override fun mapFrom(model: CategoryRemote): Category {
        return Category(
            model.id,
            model.name,
            model.imageUrl,
            model.status,
            model.sortOrder,
            favorite = false,
            locked = false
        )
    }

    override fun mapTo(model: Category): CategoryRemote {
        return CategoryRemote(model.id, model.name, model.imageUrl, model.status, model.sortOrder)
    }
}