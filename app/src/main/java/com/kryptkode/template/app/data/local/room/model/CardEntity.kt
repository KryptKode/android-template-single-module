package com.kryptkode.template.app.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kryptkode.template.app.data.local.room.model.CardEntity.Companion.TABLE_NAME

/**
 * Created by kryptkode on 2/19/2020.
 */

@Entity(tableName = TABLE_NAME)
data class CardEntity(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey val id: String,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_CATEGORY_ID)
    val categoryId: String,
    @ColumnInfo(name = COLUMN_SUBCATEGORY_ID)
    val subcategoryId: String,
    @ColumnInfo(name = COLUMN_IMG_URL)
    val imgUrl: String,
    @ColumnInfo(name = COLUMN_STATUS)
    val status: String,
    @ColumnInfo(name = COLUMN_FAVORITE)
    val favorite: Boolean
) {
    companion object {
        const val TABLE_NAME = "card"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_CATEGORY_ID = "category_id"
        const val COLUMN_SUBCATEGORY_ID = "sub_category_id"
        const val COLUMN_IMG_URL = "image_url"
        const val COLUMN_STATUS = "status"
        const val COLUMN_FAVORITE = "favorite"
    }
}