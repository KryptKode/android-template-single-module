package com.kryptkode.template.app.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.kryptkode.template.app.data.domain.error.ErrorHandler
import com.kryptkode.template.app.data.domain.model.SubCategory
import com.kryptkode.template.app.data.domain.repository.SubCategoryRepository
import com.kryptkode.template.app.data.domain.state.DataState
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.utils.extensions.handleError

/**
 * Created by kryptkode on 3/12/2020.
 */
class SubcategoryRepositoryImpl(
    private val local: Local,
    private val errorHandler: ErrorHandler
) : SubCategoryRepository {

    override fun getAllSubCategoriesUnderCategory(categoryId: String): LiveData<DataState<List<SubCategory>>> {
        return liveData {
            try {
                emit(DataState.Loading)
                val result : LiveData<DataState<List<SubCategory>>> = local.getSubCategoriesInCategory(categoryId)
                    .map {
                        DataState.Success(it)
                    }
                emitSource(result)
            }catch (e:Exception){
                handleError<List<SubCategory>>(errorHandler, e)
            }
        }
    }

    override fun getFavoriteSubcategories(): LiveData<DataState<List<SubCategory>>> {
        return  liveData {
            try {
                emit(DataState.Loading)
                val result : LiveData<DataState<List<SubCategory>>> = local.getFavoriteSubcategories()
                    .map {
                        DataState.Success(it)
                    }
                emitSource(result)
            }catch (e:Exception){
                handleError<List<SubCategory>>(errorHandler, e)
            }
        }
    }

    override suspend fun markSubcategoryAsFavorite(subCategory: SubCategory) {
        return local.updateSubcategory(subCategory.copy(favorite = true))
    }

    override suspend fun unMarkSubcategoryAsFavorite(subCategory: SubCategory) {
        return local.updateSubcategory(subCategory.copy(favorite = true))
    }
}