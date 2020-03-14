package com.kryptkode.template.subcategories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.SubCategoryRepository
import com.kryptkode.template.subcategories.mapper.SubcategoryViewMapper
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/10/2020.
 */
class SubcategoriesViewModel(
    private val subCategoryRepository: SubCategoryRepository,
    private val subcategoryViewMapper: SubcategoryViewMapper
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

    fun toggleSubcategoryFavorite(item: SubCategoryForView?) {
        launchDataLoad {
            item?.let {
                if (item.favorite) {
                    subCategoryRepository.unMarkSubcategoryAsFavorite(subcategoryViewMapper.mapFrom(item))
                } else {
                    subCategoryRepository.markSubcategoryAsFavorite(subcategoryViewMapper.mapFrom(item))
                }
            }
        }
    }

}