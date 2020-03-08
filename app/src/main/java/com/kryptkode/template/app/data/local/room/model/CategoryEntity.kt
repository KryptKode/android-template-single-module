package com.kryptkode.template.app.data.local.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kryptkode.template.app.data.local.room.model.CategoryEntity.Companion.TABLE_NAME

/**
 * Created by kryptkode on 2/19/2020.
 */

@Entity(tableName = TABLE_NAME)
data class CategoryEntity(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String,
    @ColumnInfo(name = COLUMN_IMG_URL)
    val imageUrl: String,
    @ColumnInfo(name = COLUMN_STATUS)
    val status: String,
    @ColumnInfo(name = COLUMN_SORT)
    val sortOrder: String,
    @ColumnInfo(name = COLUMN_FAVORITE)
    val favorite: Boolean,
    @ColumnInfo(name = COLUMN_LOCKED)
    val locked: Boolean
) {
    companion object {
        const val TABLE_NAME = "category"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_IMG_URL = "image_url"
        const val COLUMN_STATUS = "status"
        const val COLUMN_SORT = "sort"
        const val COLUMN_FAVORITE = "favorite"
        const val COLUMN_LOCKED = "locked"
    }
}