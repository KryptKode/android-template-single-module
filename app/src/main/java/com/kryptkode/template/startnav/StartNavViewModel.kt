package com.kryptkode.template.startnav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kryptkode.template.R
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.domain.state.successOr
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.startnav.mapper.CategoryWithSubcategoriesViewMapper
import com.kryptkode.template.startnav.model.CategoryWithSubCategoriesForView
import com.kryptkode.template.subcategories.model.SubCategoryForView
import timber.log.Timber

/**
 * Created by kryptkode on 3/4/2020.
 */
class StartNavViewModel(
    private val repository: CategoryRepository,
    private val viewMapper: CategoryWithSubcategoriesViewMapper
) : BaseViewModel() {
    private val goToSubCategory =
        MutableLiveData<Event<Pair<CategoryForView, SubCategoryForView>>>()

    fun getGoToSubCategoryEvent(): LiveData<Event<Pair<CategoryForView, SubCategoryForView>>> =
        goToSubCategory

    private val showWatchVideoConfirmDialog = MutableLiveData<Event<Unit>>()
    fun getShowWatchVideoConfirmDialogEvent(): LiveData<Event<Unit>> = showWatchVideoConfirmDialog

    private val showVideoAd = MutableLiveData<Event<Unit>>()
    fun getShowVideoAdEvent(): LiveData<Event<Unit>> = showVideoAd

    private var clickedCategory: CategoryForView? = null

    val categoryWithSubcategoriesList: LiveData<List<CategoryWithSubCategoriesForView>>

    init {
        val result = repository.getCategoryWithSubcategories()
        addErrorAndLoadingSource(result)
        categoryWithSubcategoriesList = result.map {
                it.successOr(listOf()).map { viewMapper.mapTo(it) }
        }
    }

    fun onSubCategoryClick(category: CategoryForView, subcategory: SubCategoryForView) {
        goToSubCategory.postValue(Event(Pair(category, subcategory)))
    }

    fun onCategoryClick(item: CategoryForView) {
        clickedCategory = item
        if (item.locked) {
            showWatchVideoConfirmDialog()
        } else {
            Timber.d("Category clicked ${item.id}")
        }
    }

    private fun showWatchVideoConfirmDialog() {
        showWatchVideoConfirmDialog.postValue(Event(Unit))
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
            repository.unlockCategory(viewMapper.categoryViewMapper.mapFrom(clickedCategory!!))
        }
    }

}