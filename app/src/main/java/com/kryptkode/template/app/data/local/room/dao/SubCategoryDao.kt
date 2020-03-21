package com.kryptkode.template.app.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.template.app.data.local.room.model.SubCategoryEntity

/**
 * Created by kryptkode on 2/19/2020.
 */
@Dao
abstract class SubCategoryDao : BaseDao<SubCategoryEntity>() {
    /**
     * Get entity by id.
     * @param id A Unique identifier for a given record within the Database.
     * @return
     */
    @Query("SELECT * FROM subcategory WHERE id = :id")
    abstract fun getSubCategoryByIdLive(id: String): LiveData<SubCategoryEntity>

    @Query("SELECT * FROM subcategory WHERE id = :id")
    abstract fun getSubCategoryById(id: String): SubCategoryEntity



    /**
     * Get all entities of type SubCategory
     * @return List of the Subcategories
     */
    @Query("SELECT * FROM subcategory ORDER BY sort ASC")
    abstract fun getAllSubCategories(): LiveData<List<SubCategoryEntity>>

    /**
     * Get all entities that are marked as favorite
     * @return
     */
    @Query("SELECT * FROM subcategory WHERE favorite = 1")
    abstract fun getAllFavoriteSubCategories(): LiveData<List<SubCategoryEntity>>

    /**
     * Get all entities of type SubCategory from a given parent id
     *
     * @return
     */
    @Query("SELECT * FROM subcategory WHERE category_id  = :categoryId")
    abstract fun getSubCategoriesInCategory(categoryId: String): LiveData<List<SubCategoryEntity>>

    override suspend fun handleInsertConflict(item: SubCategoryEntity) {
        val oldCategory = getSubCategoryById(item.id)
        val clone = oldCategory.copy(
            name = item.name,
            categoryId = item.categoryId,
            imageUrl = item.imageUrl,
            status = item.status,
            sortOrder = item.sortOrder
        )
        update(clone)
    }

}