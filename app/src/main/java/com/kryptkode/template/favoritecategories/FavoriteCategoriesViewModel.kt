package com.kryptkode.template.favoritecategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.domain.state.successOr
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.categories.mapper.CategoryViewMapper
import com.kryptkode.template.categories.model.CategoryForView
import timber.log.Timber

/**
 * Created by kryptkode on 3/13/2020.
 */
class FavoriteCategoriesViewModel(
    private val repository: CategoryRepository,
    private val categoryViewMapper: CategoryViewMapper
) : BaseViewModel() {

    private val goToSubCategory = MutableLiveData<Event<CategoryForView>>()
    fun getGoToSubCategoryEvent(): LiveData<Event<CategoryForView>> = goToSubCategory

    val favoriteCategoryList: LiveData<List<CategoryForView>>

    init {
        val result = repository.getFavoriteCategories()
        addErrorAndLoadingSource(result)
        favoriteCategoryList = result.map {
            it.successOr(listOf()).map { categoryViewMapper.mapTo(it) }
        }
    }


    fun refresh() {
        launchDataLoad {
            Timber.d("Refresh clicked!")
        }
    }

    fun handleCategoryItemClick(item: CategoryForView?) {
        goToSubCategory.postValue(Event(item!!))
    }

    fun handleCategoryFavoriteClick(item: CategoryForView?, favorite: Boolean) {
        launchDataLoad {
            item?.let {
                if (favorite) {
                    repository.markCategoryAsFavorite(categoryViewMapper.mapFrom(item))
                } else {
                    repository.unMarkCategoryAsFavorite(categoryViewMapper.mapFrom(item))
                }
            }
        }
    }
}