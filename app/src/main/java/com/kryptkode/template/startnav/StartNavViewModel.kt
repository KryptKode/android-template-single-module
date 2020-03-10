package com.kryptkode.template.startnav

import androidx.lifecycle.map
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.startnav.mapper.CategoryWithSubcategoriesViewMapper

/**
 * Created by kryptkode on 3/4/2020.
 */
class StartNavViewModel(
    private val repository: CategoryRepository,
    private val viewMapper: CategoryWithSubcategoriesViewMapper
) : BaseViewModel() {
    val categoryWithSubcategoriesList = repository.getCategoryWithSubcategories()
        .map {
            it.map { viewMapper.mapTo(it) }
        }

}