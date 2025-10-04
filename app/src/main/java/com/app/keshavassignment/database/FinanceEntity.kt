package com.app.keshavassignment.database


import androidx.room.Entity
import androidx.room.PrimaryKey

// 1.1. Room Entities (for persistence)
@Entity(tableName = "finance_data")
data class FinanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taxLimit: Int,
    val income: String, // Storing as String for the currency symbol/format
    val outcome: String,
    val product: String,
    val distribution: String
)

@Entity(tableName = "inventory_item")
data class InventoryItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val heading: String,
    val amountLeft: Int,
    val progress: Int,
    val category: String // 'utilized', 'soon_stock', or 'revision_needed'
)

// 1.2. JSON Models (matching your JSON structure for parsing)
data class FinanceJsonModel(
    val tax_limit: Int,
    val income: String,
    val outcome: String,
    val product: String,
    val distribution: String
)

data class InventoryItemJsonModel(
    val Heading: String,
    val amount_left: String, // Keep as String to match JSON
    val Progress: String
)

data class InventoryJsonModel(
    val utilized: List<InventoryItemJsonModel>,
    val soon_stock: List<InventoryItemJsonModel>,
    val revision_needed: List<InventoryItemJsonModel>
)

data class RootDataJsonModel(
    val finance: List<FinanceJsonModel>,
    val inventory: InventoryJsonModel
)