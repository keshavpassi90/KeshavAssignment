package com.app.keshavassignment.database

// AppDatabase.kt


import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.keshavassignment.model.InventoryItem

// ⭐ Change version from 1 to 2
@Database(entities = [InventoryItem::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao
}