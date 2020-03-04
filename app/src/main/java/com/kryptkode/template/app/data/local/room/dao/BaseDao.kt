package com.kryptkode.template.app.data.local.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update


abstract class BaseDao<T> {

    /**
     * Insert an object in the database.
     *
     * @param item the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(item: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param items the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(items: List<T>): List<Long>

    /**
     * Update an object from the database.
     *
     * @param item the object to be updated
     */
    @Update
    abstract suspend fun update(item: T)

    /**
     * Delete an object from the database
     *
     * @param item the object to be deleted
     */
    @Delete
    abstract suspend fun delete(item: T): Int

    open suspend fun handleInsertConflict(item:T){

    }

    suspend fun upsert(items: List<T>) {
        val ids = insert(items)
        ids.forEachIndexed { index, id ->
            if (id == -1L) {
                handleInsertConflict(items[index])
            }
        }
    }
}