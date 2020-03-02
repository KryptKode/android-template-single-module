package com.kryptkode.template.app.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.template.app.data.local.room.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by kryptkode on 2/19/2020.
 */

@Dao
abstract class CategoryDao {
    /**
     * Get entity by id.
     * @param id A Unique identifier for a given record within the Database.
     * @return
     */
    @Query("SELECT * FROM CategoryEntity WHERE id = :id ")
    abstract fun getCategoryById(id: String?): Flow<CategoryEntity>


    /**
     * Get all entities that are marked as favorite
     * @return
     */
    @Query("SELECT * FROM CategoryEntity WHERE favorite = 1 ")
    abstract fun getAllFavoriteCategories(): Flow<List<CategoryEntity>>


    /**
     * Get all entities of type Category
     * @return
     */
    @Query("SELECT * FROM CategoryEntity CategoryEntity ORDER BY sort ASC")
    abstract fun getAllCategories(): Flow<List<CategoryEntity>>

}