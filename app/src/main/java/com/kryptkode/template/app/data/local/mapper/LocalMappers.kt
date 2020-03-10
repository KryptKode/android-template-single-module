package com.kryptkode.template.app.data.local.mapper

/**
 * Created by kryptkode on 3/2/2020.
 */
class LocalMappers(
    val card: CardLocalDomainMapper,
    val category: CategoryLocalDomainMapper,
    val subcategory: SubcategoryLocalDomainMapper,
    val link: LinkLocalDomainMapper,
    val categoryWithSubcategoriesLocalDomainMapper: CategoryWithSubcategoriesLocalDomainMapper
)