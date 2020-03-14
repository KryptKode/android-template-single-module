package com.kryptkode.template.app.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.model.CategoryWithSubCategories
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.remote.Remote
import com.kryptkode.template.app.utils.DateHelper
import kotlinx.coroutines.withContext

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoryRepositoryImpl(
    private val dispatcher: AppDispatchers,
    private val dateHelper: DateHelper,
    private val local: Local,
    private val remote: Remote
) : CategoryRepository {

    override fun getAllCategories(): LiveData<List<Category>> = liveData {
        val cachedExpired = local.isCategoryCacheExpired()
        if (cachedExpired) {
            refreshAllCategoriesAndSubCategories()
            local.setCategoryCacheTime(dateHelper.nowInMillis())
        }
        emitSource(local.getAllCategories())
    }

    override fun getCategoryWithSubcategories(): LiveData<List<CategoryWithSubCategories>> {
        return local.getCategoryWithSubcategories()
    }


    override suspend fun refreshAllCategoriesAndSubCategories() {
        return withContext(dispatcher.network) {
            val categoriesResult = remote.getAllCategories()
            val subCategoriesResult = remote.getAllSubCategories()
            local.addCategories(categoriesResult)
            local.addSubcategories(subCategoriesResult)
        }
    }

    override fun getFavoriteCategories(): LiveData<List<Category>> {
        return local.getFavoriteCategories()
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
}