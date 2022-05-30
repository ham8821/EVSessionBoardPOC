package com.example.evsessionboardpoc.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evsessionboardpoc.R
import com.example.evsessionboardpoc.data.model.Session
import com.example.evsessionboardpoc.data.model.SummaryItemType
import kotlinx.android.synthetic.main.summary_item.view.*
import java.text.NumberFormat

class SummaryAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<SummaryItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.summary_item, parent, false)
        view.layoutParams.width = (parent.width * 0.4).toInt()
        viewHolder = SummaryViewHolder(view, parent.context)
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SummaryViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateSummaryItems(sessionsMap: java.util.HashMap<String, List<Session>>) {
        setItems(sessionsMap)
        notifyDataSetChanged()
    }

    private fun setItems(map: java.util.HashMap<String, List<Session>>) {
        this.items = createSummaryListWithMap(map)
        notifyDataSetChanged()
    }

    private fun createSummaryListWithMap(map: java.util.HashMap<String, List<Session>>): List<SummaryItem> {
        val list: java.util.ArrayList<SummaryItem> = java.util.ArrayList()
        val numberFormat = NumberFormat.getCurrencyInstance()
        var totalSavings = 0.00
        var totalCost = 0.00
        var totalChargeDuration = "7h 24m"
        map.forEach{ mapItem ->
            mapItem.value.forEach {
                totalSavings += it.saving
                totalCost += it.cost
            }
        }
        val formattedSavings = numberFormat.format(totalSavings)
        val formattedCost = numberFormat.format(totalCost)
        list.add(SummaryItem(SummaryItemType.SAVINGS.name, formattedSavings))
        list.add(SummaryItem(SummaryItemType.COST.name, formattedCost))
        list.add(SummaryItem(SummaryItemType.DURATION.name, totalChargeDuration))
        return list
    }

    inner class SummaryViewHolder(item: View, context: Context) : RecyclerView.ViewHolder(item) {
        private val title = item.summary_title
        private val value = item.summary_value
        private val context = context
        fun bind(position: Int) {
            val item = items[position] as SummaryItem
            when(item.title){
                SummaryItemType.SAVINGS.name -> {
                    value.setTextColor(context.getColor(R.color.green))
                }
                SummaryItemType.DURATION.name -> {
                    title.setCompoundDrawables(null, null, null, null)
                }
            }
            title.text = item.title
            value.text = item.value
        }
    }
}


class SummaryItem(var title: String, var value: String)
