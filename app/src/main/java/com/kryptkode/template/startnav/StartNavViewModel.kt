package com.kryptkode.template.startnav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.startnav.mapper.CategoryWithSubcategoriesViewMapper
import com.kryptkode.template.subcategories.model.SubCategoryForView

/**
 * Created by kryptkode on 3/4/2020.
 */
class StartNavViewModel(
    private val repository: CategoryRepository,
    private val viewMapper: CategoryWithSubcategoriesViewMapper
) : BaseViewModel() {
    private val goToSubCategory = MutableLiveData<Event<Pair<CategoryForView, SubCategoryForView>>>()
    fun getGoToSubCategoryEvent(): LiveData<Event<Pair<CategoryForView, SubCategoryForView>>> = goToSubCategory

    fun onSubCategoryClick(
        category: CategoryForView,
        subcategory: SubCategoryForView
    ) {
        goToSubCategory.postValue(Event(Pair(category, subcategory)))
    }

    val categoryWithSubcategoriesList = repository.getCategoryWithSubcategories()
        .map {
            it.map { viewMapper.mapTo(it) }
        }

}