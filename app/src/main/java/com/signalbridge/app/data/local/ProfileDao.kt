package com.signalbridge.app.data.local

import androidx.room.*
import com.signalbridge.app.domain.model.ForwardingProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile_entity ORDER BY name COLLATE NOCASE")
    fun all(): Flow<List<ProfileEntity>>

    @Query("SELECT * FROM profile_entity WHERE id = :id")
    suspend fun findById(id: String): ProfileEntity?

    @Query("SELECT * FROM profile_entity WHERE isDefault = 1 LIMIT 1")
    suspend fun findDefault(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileEntity)

    @Delete
    suspend fun delete(profile: ProfileEntity)

    @Query("UPDATE profile_entity SET isDefault = 0")
    suspend fun clearDefault()

    @Query("UPDATE profile_entity SET isDefault = 1 WHERE id = :id")
    suspend fun setDefault(id: String)
}
