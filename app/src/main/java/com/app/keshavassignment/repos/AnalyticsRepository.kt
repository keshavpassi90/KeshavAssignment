package com.app.keshavassignment.repos

import com.app.keshavassignment.database.AssetsDataSource
import com.app.keshavassignment.database.InventoryDao
import com.app.keshavassignment.model.AnalyticsModel
import com.app.keshavassignment.model.InventoryItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AnalyticsRepository @Inject constructor(
    private val assetsDataSource: AssetsDataSource,
    private val inventoryDao: InventoryDao
) {
    // 1. Fetch ALL data from local JSON
    fun getBusinessData(): AnalyticsModel? {
        return assetsDataSource.loadDataFromJson()
    }



    // 2. Insert Inventory data into Room DB
    suspend fun loadInventoryDataIntoDb() {
        val data = getBusinessData()
        data?.inventory?.let { inventory ->

            // ⭐ FIX: Clear old data to prevent duplication when using static JSON
            inventoryDao.deleteAllInventoryItems()

            val itemsToInsert = mutableListOf<InventoryItem>()

            // Map and tag utilized items
            inventory.utilized.mapTo(itemsToInsert) { item ->
                item.copy(category = "utilized")
            }
            // Map and tag soon_stock items
            inventory.soonStock.mapTo(itemsToInsert) { item ->
                item.copy(category = "soon_stock")
            }
            // Map and tag revision_needed items
            inventory.revisionNeeded.mapTo(itemsToInsert) { item ->
                item.copy(category = "revision_needed")
            }

            inventoryDao.insertInventoryItems(itemsToInsert)
        }
    }

    // 3. Fetch Utilized Inventory from Room DB
    fun getUtilizedInventory(): Flow<List<InventoryItem>> {
        return inventoryDao.getInventoryItemsByCategory("utilized")
    }

    // 4. Fetch Soon Stock Inventory from Room DB
    fun getSoonStockInventory(): Flow<List<InventoryItem>> {
        return inventoryDao.getInventoryItemsByCategory("soon_stock")
    }

    // 5. Fetch Revision Needed Inventory from Room DB
    fun getRevisionNeededInventory(): Flow<List<InventoryItem>> {
        return inventoryDao.getInventoryItemsByCategory("revision_needed")
    }
}