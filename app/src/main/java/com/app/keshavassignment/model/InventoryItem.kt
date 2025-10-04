package com.app.keshavassignment.model

// InventoryItem.kt

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "inventory_items")
data class InventoryItem(
    // ⭐ Reintroduce the auto-generated primary key
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @SerializedName("Heading") val heading: String,
    @SerializedName("amount_left") val amountLeft: String,
    @SerializedName("Progress") val progress: String,

    // Used to categorize the item in the DB
    val category: String
)