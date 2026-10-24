package com.signalbridge.app.data.local

import androidx.room.*
import com.signalbridge.app.domain.model.ForwardingOperation
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {
    @Query("SELECT * FROM operation_entity ORDER BY timestampMs DESC")
    fun all(): Flow<List<OperationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(operation: OperationEntity)

    @Query("DELETE FROM operation_entity")
    suspend fun clearAll()
}
