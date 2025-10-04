package com.app.keshavassignment.model

import com.google.gson.annotations.SerializedName

data class AnalyticsModel(
    val finance: List<FinanceRecord>,
    val inventory: InventoryData
)

data class FinanceRecord(
    @SerializedName("tax_limit") val taxLimit: Int,
    val income: String,
    val outcome: String,
    val product: String,
    val distribution: String
)

data class InventoryData(
    val utilized: List<InventoryItem>,
    @SerializedName("soon_stock") val soonStock: List<InventoryItem>,
    @SerializedName("revision_needed") val revisionNeeded: List<InventoryItem>
)