package com.kryptkode.template.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kryptkode.template.R
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.domain.state.successOr
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.categories.mapper.CategoryViewMapper
import com.kryptkode.template.categories.model.CategoryForView
import timber.log.Timber

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

    private val showWatchVideoConfirmDialog = MutableLiveData<Event<Unit>>()
    fun getShowWatchVideoConfirmDialogEvent(): LiveData<Event<Unit>> = showWatchVideoConfirmDialog

    private val showVideoAd = MutableLiveData<Event<Unit>>()
    fun getShowVideoAdEvent(): LiveData<Event<Unit>> = showVideoAd

    private var clickedCategory: CategoryForView? = null

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
        }) {
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
        clickedCategory = item
        if (item?.locked == true) {
            showWatchVideoConfirmDialog()
        } else {
            openSubcategory(item)
        }
    }

    private fun openSubcategory(item: CategoryForView?) {
        goToSubCategory.postValue(Event(item!!))
    }

    private fun showWatchVideoConfirmDialog() {
        showWatchVideoConfirmDialog.postValue(Event(Unit))
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

    fun onWatchVideo() {
        showVideoAd.postValue(Event(Unit))
    }

    fun videoAdShown() {
        Timber.d("Showing video ad")
    }

    fun videoAdNotLoaded() {
        showMessage(R.string.video_not_loaded_error_msg)
    }

    fun onVideoRewarded() {
        launchDataLoad {
            repository.unlockCategory(categoryViewMapper.mapFrom(clickedCategory!!))
        }
    }
}