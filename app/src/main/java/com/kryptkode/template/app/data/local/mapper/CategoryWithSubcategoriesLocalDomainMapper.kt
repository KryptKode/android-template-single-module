package com.kryptkode.template.app.data.local.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.CategoryWithSubCategories
import com.kryptkode.template.app.data.local.room.model.CategoryWithSubCategoriesEntity

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoryWithSubcategoriesLocalDomainMapper(
    private val categoryLocalDomainMapper: CategoryLocalDomainMapper,
    private val subcategoryLocalDomainMapper: SubcategoryLocalDomainMapper
) : Mapper<CategoryWithSubCategoriesEntity, CategoryWithSubCategories> {

    override fun mapFrom(model: CategoryWithSubCategoriesEntity): CategoryWithSubCategories {
        return CategoryWithSubCategories(
            categoryLocalDomainMapper.mapFrom(model.category),
            model.subcategories.map {
                subcategoryLocalDomainMapper.mapFrom(it)
            })
    }

    override fun mapTo(model: CategoryWithSubCategories): CategoryWithSubCategoriesEntity {
        return CategoryWithSubCategoriesEntity(
            categoryLocalDomainMapper.mapTo(model.category),
            model.subcategories.map {
                subcategoryLocalDomainMapper.mapTo(it)
            }
        )
    }
}