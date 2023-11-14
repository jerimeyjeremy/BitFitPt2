package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FitDAO {
    @Query("SELECT * FROM fit_table")
    fun getAll(): Flow<List<FitEntity>>

    @Insert
    fun insertAll(foods: List<FitEntity>)

    @Insert
    fun insert(fit: FitEntity)

    @Query("DELETE FROM fit_table")
    fun deleteAll()
    //2
}