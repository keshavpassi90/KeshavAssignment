package com.app.keshavassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.keshavassignment.R
import com.app.keshavassignment.model.FinanceRecord

/**
 * RecyclerView Adapter for ViewPager2 carousel
 */
class FinanceAdapter(private val items: List<FinanceRecord>) :
    RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>() {

    inner class FinanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val indecator: ProgressBar = itemView.findViewById(R.id.custom_indicator)
        val tvIncomeValue: TextView = itemView.findViewById(R.id.tvIncomeValue)
        val tvOutComeValue: TextView = itemView.findViewById(R.id.tvOutComeValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_finance, parent, false)
        return FinanceViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinanceViewHolder, position: Int) {
        // Bind image and text to the carousel item
        holder.indecator.setProgress(items[position].taxLimit)
        holder.tvOutComeValue.text=items[position].outcome
        holder.tvIncomeValue.text=items[position].income
    }

    override fun getItemCount(): Int = items.size
}
