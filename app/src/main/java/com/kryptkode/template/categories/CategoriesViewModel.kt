package com.kryptkode.template.categories

import androidx.lifecycle.map
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.categories.mapper.CategoriesViewMapper
import com.kryptkode.template.categories.model.CategoryForView
import timber.log.Timber

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoriesViewModel(
    private val repository: CategoryRepository,
    private val categoriesViewMapper: CategoriesViewMapper
) : BaseViewModel() {
    val categoriesList = repository.getAllCategories().map {
        it.map { categoriesViewMapper.mapTo(it) }
    }

    fun refreshCategories() {
        launchDataLoad {
            repository.refreshAllCategories()
        }
    }

    fun handleCategoryItemClick(item: CategoryForView?) {
        Timber.d("Item click: $item")
    }

    fun handleCategoryFavoriteClick(item: CategoryForView?, favorite: Boolean) {
        launchDataLoad {
            item?.let {
                if (favorite) {
                    repository.markCardAsFavorite(categoriesViewMapper.mapFrom(item))
                } else {
                    repository.unMarkCardAsFavorite(categoriesViewMapper.mapFrom(item))
                }
            }
        }
    }
}