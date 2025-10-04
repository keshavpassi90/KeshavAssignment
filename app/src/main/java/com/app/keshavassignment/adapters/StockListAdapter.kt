package com.app.keshavassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.keshavassignment.R
import com.app.keshavassignment.model.InventoryItem

class StockListAdapter(val itemArray: List<InventoryItem>) :
    RecyclerView.Adapter<StockListAdapter.StockListHolder>() {

    // ViewHolder class (holds reference of item views)
    class StockListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_product_name)
        val tvAmount: TextView = itemView.findViewById(R.id.tv_amount)
        val inventoryIndicator: ProgressBar = itemView.findViewById(R.id.inventory_indicator)
    }

    // Inflate item layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory, parent, false)
        return StockListHolder(view)
    }

    // Bind data
    override fun onBindViewHolder(holder: StockListHolder, position: Int) {
        holder.tvTitle.text = itemArray[position].heading
        holder.inventoryIndicator.setProgress(itemArray[position].progress.toInt())
        holder.tvAmount.text = itemArray[position].amountLeft+" pcs"
    }

    override fun getItemCount(): Int = itemArray.size
}
