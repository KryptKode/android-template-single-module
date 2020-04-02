package com.kryptkode.template.app.data.local.room.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.kryptkode.template.app.data.local.room.model.CardEntity

/**
 * Created by kryptkode on 12/30/2019.
 */
object Migrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE ${CardEntity.TABLE_NAME} ADD COLUMN ${CardEntity.COLUMN_POSITION} INTEGER NOT NULL DEFAULT 0")
        }
    }

}