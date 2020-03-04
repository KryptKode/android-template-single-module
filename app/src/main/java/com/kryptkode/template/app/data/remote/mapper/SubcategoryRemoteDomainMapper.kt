package com.kryptkode.template.app.data.remote.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.app.data.remote.response.SubCategoryRemote

/**
 * Created by kryptkode on 3/2/2020.
 */
class SubcategoryRemoteDomainMapper : Mapper<SubCategoryRemote, SubCategory> {
    override fun mapFrom(model: SubCategoryRemote): SubCategory {
        return SubCategory(
            model.id,
            model.name,
            model.parentId,
            model.imageUrl,
            model.status,
            model.sortOrder,
            false
        )
    }

    override fun mapTo(model: SubCategory): SubCategoryRemote {
        return SubCategoryRemote(
            model.id,
            model.name,
            model.parentId,
            model.imageUrl,
            model.status,
            model.sortOrder
        )
    }
}