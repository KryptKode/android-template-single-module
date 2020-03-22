package com.kryptkode.template.app.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.domain.error.ErrorHandler
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.model.CategoryWithSubCategories
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.domain.state.DataState
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.remote.Remote
import com.kryptkode.template.app.utils.Constants
import com.kryptkode.template.app.utils.DateHelper
import com.kryptkode.template.app.utils.extensions.handleError
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoryRepositoryImpl(
    private val dispatcher: AppDispatchers,
    private val dateHelper: DateHelper,
    private val local: Local,
    private val remote: Remote,
    private val errorHandler: ErrorHandler
) : CategoryRepository {

    override fun getAllCategories(): LiveData<DataState<List<Category>>> {
        return liveData {
            emit(DataState.Loading)
            try {
                val allCategories: LiveData<DataState<List<Category>>> = local.getAllCategories()
                    .map { DataState.Success(it) }
                val cachedExpired = local.isCategoryCacheExpired()
                if (cachedExpired) {
                    refreshAllCategoriesAndSubCategories()
                    local.setCategoryCacheTime(dateHelper.nowInMillis())
                }
                emitSource(allCategories)
            } catch (e: Exception) {
                handleError<DataState<List<Category>>>(errorHandler, e)
            }
        }
    }


    override fun getCategoryWithSubcategories(): LiveData<DataState<List<CategoryWithSubCategories>>> {
        return liveData {
            emit(DataState.Loading)
            try {
                val result: LiveData<DataState<List<CategoryWithSubCategories>>> =
                    local.getCategoryWithSubcategories()
                        .map { DataState.Success(it) }
                emitSource(result)
            } catch (e: Exception) {
                handleError<DataState<List<Category>>>(errorHandler, e)
            }
        }
    }


    override suspend fun refreshAllCategoriesAndSubCategories() {
        return withContext(dispatcher.network) {
            val categoriesResult = remote.getAllCategories()
            val subCategoriesResult = remote.getAllSubCategories()
            lockCategory(categoriesResult)
            local.addCategories(categoriesResult)
            local.addSubcategories(subCategoriesResult)
        }
    }

    private fun lockCategory(categoriesResult: List<Category>) {
        Constants.lockedCategoryPositions.forEach {
            Timber.e("Locked position: $it")
            if(categoriesResult.size > it && it > 0){
                Timber.e("Locking category")
                val category = categoriesResult[it-1]
                category.locked = local.isCategoryLocked(category.id)
                Timber.d("Locked category $category")
            }
        }
    }

    override fun getFavoriteCategories(): LiveData<DataState<List<Category>>> {
        return liveData {
            emit(DataState.Loading)
            try {
                val result: LiveData<DataState<List<Category>>> =
                    local.getFavoriteCategories()
                        .map { DataState.Success(it) }
                emitSource(result)
            } catch (e: Exception) {
                handleError<DataState<List<Category>>>(errorHandler, e)
            }
        }
    }

    override suspend fun markCategoryAsFavorite(category: Category) {
        return local.updateCategory(category.copy(favorite = true))
    }

    override suspend fun unMarkCategoryAsFavorite(category: Category) {
        return local.updateCategory(category.copy(favorite = false))
    }

    override suspend fun getCategory(categoryId: String): Category {
        return local.getCategory(categoryId)
    }

    override suspend fun unlockCategory(category: Category) {
        local.updateCategory(category.copy(locked = false))
        local.setDateWhenCategoryWasUnlocked(category.id, dateHelper.nowInMillis())
    }
}