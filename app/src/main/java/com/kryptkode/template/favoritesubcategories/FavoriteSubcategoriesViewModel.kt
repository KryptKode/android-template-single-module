package com.kryptkode.template.favoritesubcategories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.kryptkode.template.app.base.viewmodel.BaseViewModel
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.domain.repository.SubCategoryRepository
import com.kryptkode.template.app.data.model.Event
import com.kryptkode.template.categories.mapper.CategoryViewMapper
import com.kryptkode.template.categories.model.CategoryForView
import com.kryptkode.template.subcategories.mapper.SubcategoryViewMapper
import com.kryptkode.template.subcategories.model.SubCategoryForView
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by kryptkode on 3/13/2020.
 */
class FavoriteSubcategoriesViewModel (
    private val categoryRepository: CategoryRepository,
    private val subCategoryRepository: SubCategoryRepository,
    private val categoryViewMapper: CategoryViewMapper,
    private val subcategoryViewMapper: SubcategoryViewMapper
): BaseViewModel() {

    private val goToSubCategory = MutableLiveData<Event<Pair<CategoryForView, SubCategoryForView>>>()
    fun getGoToSubCategoryEvent(): LiveData<Event<Pair<CategoryForView, SubCategoryForView>>> = goToSubCategory

    val favoriteSubcategoryList = subCategoryRepository.getFavoriteSubcategories().map {
        it.map { subcategoryViewMapper.mapTo(it) }
    }

    fun refresh() {
        launchDataLoad {

        }
    }

    fun handleSubcategoryItemClick(item: SubCategoryForView?) {
        item ?: return
        viewModelScope.launch {
            try {
                val category = categoryViewMapper.mapTo(categoryRepository.getCategory(item.categoryId))
                goToSubCategory.postValue(Event(Pair(category, item)))
            }catch (e:Exception){
                Timber.d(e)
            }

        }
    }

    fun handleSubcategoryFavoriteClick(item: SubCategoryForView?, favorite: Boolean) {
        launchDataLoad {
            item?.let {
                if (favorite) {
                    subCategoryRepository.markSubcategoryAsFavorite(subcategoryViewMapper.mapFrom(item))
                } else {
                    subCategoryRepository.unMarkSubcategoryAsFavorite(subcategoryViewMapper.mapFrom(item))
                }
            }
        }
    }
}