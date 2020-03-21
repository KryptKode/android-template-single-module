package com.kryptkode.template.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.domain.state.successOr
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.categories.mapper.CategoryViewMapper
import com.kryptkode.template.categories.model.CategoryForView

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoriesViewModel(
    private val repository: CategoryRepository,
    private val categoryViewMapper: CategoryViewMapper
) : BaseViewModel() {

    private val goToSubCategory = MutableLiveData<Event<CategoryForView>>()
    fun getGoToSubCategoryEvent(): LiveData<Event<CategoryForView>> = goToSubCategory

    private val _categoriesList = MediatorLiveData<List<CategoryForView>>()
    val categoriesList: LiveData<List<CategoryForView>> = _categoriesList

    init {
        initializeData()
    }

    private fun initializeData() {
        val result = repository.getAllCategories()
        addErrorAndLoadingSource(result)
        _categoriesList.addSource(result.map {
            it.successOr(listOf()).map {
                categoryViewMapper.mapTo(it)
            }
        }){
            _categoriesList.postValue(it)
        }
    }

    fun refresh() {
        launchDataLoad {
            repository.refreshAllCategoriesAndSubCategories()
//            initializeData()
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