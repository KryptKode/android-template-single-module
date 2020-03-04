package com.kryptkode.template.app.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.kryptkode.template.app.data.dispatchers.AppDispatchers
import com.kryptkode.template.app.data.domain.model.Category
import com.kryptkode.template.app.data.domain.repository.CategoryRepository
import com.kryptkode.template.app.data.local.Local
import com.kryptkode.template.app.data.remote.Remote
import kotlinx.coroutines.withContext

/**
 * Created by kryptkode on 3/2/2020.
 */
class CategoryRepositoryImpl(
    private val dispatcher: AppDispatchers,
    private val local: Local,
    private val remote: Remote
) : CategoryRepository {

    override fun getAllCategories(): LiveData<List<Category>> = liveData {
        val cachedExpired = local.isCardCacheExpired()
        if (cachedExpired) {
            refreshAllCategories()
        }
        emitSource(local.getAllCategories())
    }


    override suspend fun refreshAllCategories() {
        return withContext(dispatcher.network) {
            val result = remote.getAllCategories()
            local.addCategories(result)
        }
    }

    override fun getFavoriteCategories(): LiveData<List<Category>> {
        return local.getFavoriteCategories()
    }

    override suspend fun markCardAsFavorite(category: Category) {
        return local.updateCategory(category.copy(favorite = true))
    }

    override suspend fun unMarkCardAsFavorite(category: Category) {
        return local.updateCategory(category.copy(favorite = false))
    }
}