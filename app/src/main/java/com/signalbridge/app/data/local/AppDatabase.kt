package com.signalbridge.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.signalbridge.app.domain.model.ForwardingOperation
import com.signalbridge.app.domain.model.ForwardingProfile

@Database(
    entities = [ProfileEntity::class, OperationEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun operationDao(): OperationDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "signalbridge_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // Migration from version 1 to 2: add notes column to profiles if needed.
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Room handles added columns automatically in most cases; kept for
            // explicit future schema management.
        }
    }
}

data class ProfileEntity(
    val id: String,
    val name: String,
    val description: String?,
    val recoveryPolicy: String,
    val isDefault: Boolean = false,
    val createdAtMs: Long = System.currentTimeMillis(),
    val updatedAtMs: Long = System.currentTimeMillis(),
    val notes: String? = null
)

data class OperationEntity(
    val id: String,
    val subscriptionId: Int,
    val condition: String,
    val destination: String?,
    val resultType: String,
    val resultSummaryKey: String,
    val timestampMs: Long,
    val carrierConfirmed: Boolean,
    val requiresUserAction: Boolean
)
