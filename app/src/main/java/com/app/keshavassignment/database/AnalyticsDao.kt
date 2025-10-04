package com.app.keshavassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.keshavassignment.model.InventoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInventoryItems(items: List<InventoryItem>)

    @Query("SELECT * FROM inventory_items WHERE category = :category")
    fun getInventoryItemsByCategory(category: String): Flow<List<InventoryItem>>

    // ⭐ Added method to clear the table before re-inserting static data
    @Query("DELETE FROM inventory_items")
    suspend fun deleteAllInventoryItems()
}