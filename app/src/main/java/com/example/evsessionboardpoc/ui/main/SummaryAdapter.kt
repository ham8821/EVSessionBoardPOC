package com.example.evsessionboardpoc.ui.main

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evsessionboardpoc.R
import com.example.evsessionboardpoc.data.model.*
import kotlinx.android.synthetic.main.session_item_total.view.*
import kotlinx.android.synthetic.main.summary_item.view.*
import java.text.NumberFormat
import java.time.Duration

class SummaryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<SummaryItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.summary_item, parent, false)
        viewHolder = SummaryViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SummaryAdapter.SummaryViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setSessions(sessions: List<Session>) {
        val map = groupDataIntoHashMap(sessions)
        val list: ArrayList<SummaryItem> = ArrayList()
        map.forEach{ mapItem ->
            mapItem.key
            list.add(SummaryItem(mapItem.key, mapItem.value))
        }
        this.items = list
    }

    fun updateSessions(sessions: List<Session>) {
        setSessions(sessions)
        notifyDataSetChanged()
    }

    inner class SummaryViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val title = item.summary_title
        private val value = item.summary_value

        fun bind(position: Int) {
            val item = items[position] as SummaryItem

            title.text = item.title
            value.text = item.value
        }
    }
}

private fun groupDataIntoHashMap(list: List<Session>): HashMap<String, String> {
    val map = HashMap<String, String>()
    var totalSavings = 0.00
    var totalCost = 0.00
    var totalChargeDuration = "7h 24m"
    list.forEach { session ->
        totalSavings += session.saving
        totalCost += session.cost
    }
    val numberFormat = NumberFormat.getCurrencyInstance()
    val formattedSavings = numberFormat.format(totalSavings)
    val formattedCost = numberFormat.format(totalCost)

    map["Total charge duration"] = totalChargeDuration
    map["Total cost"] = formattedCost
    map["Total savings"] = formattedSavings
    return map
}


class SummaryItem(var title: String, var value: String)
