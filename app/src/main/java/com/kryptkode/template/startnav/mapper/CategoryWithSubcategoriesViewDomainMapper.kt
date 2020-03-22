package com.kryptkode.template.startnav.mapper

import com.kryptkode.template.app.data.domain.mapper.Mapper
import com.kryptkode.template.app.data.domain.model.CategoryWithSubCategories
import com.kryptkode.template.categories.mapper.CategoryViewMapper
import com.kryptkode.template.startnav.model.CategoryWithSubCategoriesForView
import com.kryptkode.template.subcategories.mapper.SubcategoryViewMapper

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoryWithSubcategoriesViewMapper(
    val categoryViewMapper: CategoryViewMapper,
    private val subcategoryViewMapper: SubcategoryViewMapper
) : Mapper<CategoryWithSubCategoriesForView, CategoryWithSubCategories> {

    override fun mapFrom(model: CategoryWithSubCategoriesForView): CategoryWithSubCategories {
        return CategoryWithSubCategories(
            categoryViewMapper.mapFrom(model.category),
            model.subcategories.map {
                subcategoryViewMapper.mapFrom(it)
            })
    }

    override fun mapTo(model: CategoryWithSubCategories): CategoryWithSubCategoriesForView {
        return CategoryWithSubCategoriesForView(
            categoryViewMapper.mapTo(model.category),
            model.subcategories.map {
                subcategoryViewMapper.mapTo(it)
            }
        )
    }
}