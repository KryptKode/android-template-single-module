package com.kryptkode.template.app.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.template.app.data.local.room.model.CategoryEntity

/**
 * Created by kryptkode on 2/19/2020.
 */

@Dao
abstract class CategoryDao : BaseDao<CategoryEntity>() {
    /**
     * Get entity by id.
     * @param id A Unique identifier for a given record within the Database.
     * @return
     */
    @Query("SELECT * FROM CategoryEntity WHERE id = :id ")
    abstract fun getCategoryLiveById(id: String?): LiveData<CategoryEntity>

    @Query("SELECT * FROM CategoryEntity WHERE id = :id ")
    abstract fun getCategoryById(id: String?): CategoryEntity


    /**
     * Get all entities that are marked as favorite
     * @return
     */
    @Query("SELECT * FROM CategoryEntity WHERE favorite = 1 ")
    abstract fun getAllFavoriteCategories(): LiveData<List<CategoryEntity>>

    /**
     * Get all entities of type Category
     * @return
     */
    @Query("SELECT * FROM CategoryEntity")
    abstract fun getAllCategories(): LiveData<List<CategoryEntity>>

    override suspend fun handleInsertConflict(item: CategoryEntity) {
        val oldCategory = getCategoryById(item.id)
        val clone = oldCategory.copy(
            name = item.name,
            imageUrl = item.imageUrl,
            status = item.status,
            sortOrder = item.sortOrder
        )
        update(clone)
    }
}