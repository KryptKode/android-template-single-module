package com.kryptkode.template.app.data.local.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.app.data.local.room.model.SubCategoryEntity

/**
 * Created by kryptkode on 3/2/2020.
 */
class SubcategoryLocalDomainMapper : Mapper<SubCategoryEntity, SubCategory> {
    override fun mapFrom(model: SubCategoryEntity): SubCategory {
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

    override fun mapTo(model: SubCategory): SubCategoryEntity {
        return SubCategoryEntity(
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