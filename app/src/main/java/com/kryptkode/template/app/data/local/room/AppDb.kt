package com.kryptkode.template.app.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kryptkode.template.app.data.local.room.migrations.Migrations
import com.kryptkode.template.app.data.local.room.model.SampleModel

@Database(
    entities = [SampleModel::class],
    version = 1, exportSchema = true
)
abstract class AppDb : RoomDatabase() {

    companion object {
        private const val DB_NAME = "releasing.db"
        private var INSTANCE: AppDb? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDb {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDb::class.java,
                            DB_NAME
                        ).addMigrations(Migrations.MIGRATION_1_2)
                            .build()
                    }
                    return INSTANCE as AppDb
                }
            }
            return INSTANCE as AppDb
        }
    }

}