package com.app.keshavassignment.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.keshavassignment.model.AnalyticsModel
import com.app.keshavassignment.model.FinanceRecord
import com.app.keshavassignment.model.InventoryItem
import com.app.keshavassignment.repos.AnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


// ⭐ Hilt injects the Repository
@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val repository: AnalyticsRepository
) : ViewModel() {

    // --- Finance Data (JSON) ---
    private val _financeData = MutableLiveData<List<FinanceRecord>>()
    val financeData: LiveData<List<FinanceRecord>> = _financeData

    // --- Inventory Data (Room DB - utilizing Flow.asLiveData()) ---

    // All three lists use the repository methods and Flow.asLiveData()
    val utilizedInventory: LiveData<List<InventoryItem>> =
        repository.getUtilizedInventory().asLiveData()

    val soonStockInventory: LiveData<List<InventoryItem>> =
        repository.getSoonStockInventory().asLiveData()

    val revisionNeededInventory: LiveData<List<InventoryItem>> =
        repository.getRevisionNeededInventory().asLiveData()

    init {
        loadAllData()
    }



    private fun loadAllData() {
        viewModelScope.launch {
            // 1. Load JSON data (for finance)
            val businessData: AnalyticsModel? = repository.getBusinessData()
            businessData?.finance?.let {
                _financeData.value = it
            }

            // 2. Load Inventory data into the Room DB (triggers all Flow observers)
            repository.loadInventoryDataIntoDb()
        }
    }
}