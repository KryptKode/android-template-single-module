package com.kryptkode.template.subcategories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.SubCategoryRepository
import com.kryptkode.template.subcategories.mapper.SubcategoryViewMapper

/**
 * Created by kryptkode on 3/10/2020.
 */
class SubcategoriesViewModel(
    subCategoryRepository: SubCategoryRepository,
    subcategoryViewMapper: SubcategoryViewMapper
) : BaseViewModel() {

    private val categoryIdLive = MutableLiveData<String>()

    val subcategoryList = categoryIdLive.switchMap {
        subCategoryRepository.getAllSubCategoriesUnderCategory(it).map {
            it.map {
                subcategoryViewMapper.mapTo(it)
            }
        }
    }

    fun loadSubcategories(categoryId: String) {
        categoryIdLive.postValue(categoryId)
    }

}