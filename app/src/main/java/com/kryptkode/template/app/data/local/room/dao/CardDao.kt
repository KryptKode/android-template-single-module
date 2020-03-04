package com.kryptkode.template.app.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.template.app.data.local.room.model.CardEntity

/**
 * Created by kryptkode on 2/23/2020.
 */
@Dao
abstract class CardDao : BaseDao<CardEntity>() {

    /**
     * Get entity by id.
     * @param id A Unique identifier for a given record within the Database.
     * @return
     */
    @Query("SELECT * FROM CardEntity WHERE id = :id")
    abstract fun getCardEntityById(id: String?): CardEntity


    /**
     * Get all entities of type CardEntity
     * @return List of the wallpapers
     */
    @Query("SELECT * FROM CardEntity")
    abstract fun getAllCards(): LiveData<List<CardEntity>>


    /**
     * Get all entities that are marked as favorite
     * @return
     */
    @Query("SELECT * FROM CardEntity WHERE favorite = 1")
    abstract fun getAllFavoriteCards(): LiveData<List<CardEntity>>


    /**
     * Get all entities of type CardEntity from a given subcategory id
     *
     * @return
     */
    @Query("SELECT * FROM CardEntity WHERE subCategoryId  = :subCategoryId")
    abstract fun getCardEntitysWithSubCategory(subCategoryId: String): LiveData<List<CardEntity>>


    @Query("SELECT * FROM CardEntity WHERE categoryId  = :categoryId")
    abstract fun getCardEntitysWithCategory(categoryId: String): LiveData<List<CardEntity>>
}