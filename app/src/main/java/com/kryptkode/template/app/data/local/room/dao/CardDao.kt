package com.kryptkode.template.app.data.local.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.kryptkode.template.app.data.local.room.model.CardEntity
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.COLUMN_CATEGORY_ID
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.COLUMN_FAVORITE
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.COLUMN_ID
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.COLUMN_IMG_URL
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.COLUMN_NAME
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.COLUMN_POSITION
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.COLUMN_STATUS
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.COLUMN_SUBCATEGORY_ID
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.TABLE_NAME

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
    @Query("SELECT * FROM card WHERE id = :id")
    abstract fun getCardEntityById(id: String?): CardEntity


    /**
     * Get all entities of type CardEntity
     * @return List of the wallpapers
     */
    @Query("SELECT * FROM card")
    abstract fun getAllCards(): LiveData<List<CardEntity>>


    /**
     * Get all entities that are marked as favorite
     * @return
     */
    @Query("SELECT * FROM card WHERE favorite = 1")
    abstract fun getAllFavoriteCards(): LiveData<List<CardEntity>>


    /**
     * Get all entities of type CardEntity from a given subcategory id
     *
     * @return
     */
    @Query(
        "SELECT " +
                "$COLUMN_ID, " +
                "$COLUMN_NAME, " +
                "$COLUMN_CATEGORY_ID, " +
                "$COLUMN_SUBCATEGORY_ID, " +
                "$COLUMN_IMG_URL, " +
                "$COLUMN_STATUS, " +
                "$COLUMN_FAVORITE, " +
                "(SELECT COUNT(*) from $TABLE_NAME b  WHERE a.id >= b.id AND sub_category_id  = :subCategoryId ORDER BY $COLUMN_ID) as $COLUMN_POSITION " +
                "FROM card a WHERE sub_category_id  = :subCategoryId ORDER BY $COLUMN_ID"
    )
    abstract fun getCardEntitiesWithSubCategory(subCategoryId: String): LiveData<List<CardEntity>>


    @Query("SELECT * FROM card WHERE category_id  = :categoryId")
    abstract fun getCardEntitiesWithCategory(categoryId: String): LiveData<List<CardEntity>>

    override suspend fun handleInsertConflict(item: CardEntity) {
        val oldCategory = getCardEntityById(item.id)
        val clone = oldCategory.copy(
            name = item.name,
            categoryId = item.categoryId,
            subcategoryId = item.subcategoryId,
            imgUrl = item.imgUrl,
            status = item.status
        )
        update(clone)
    }
}