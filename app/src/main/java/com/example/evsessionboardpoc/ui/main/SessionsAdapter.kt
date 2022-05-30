package com.example.evsessionboardpoc.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evsessionboardpoc.R
import com.example.evsessionboardpoc.data.model.*
import com.example.evsessionboardpoc.ui.main.EvSessionsFragment.Companion.TYPE_SESSIONS
import com.example.evsessionboardpoc.ui.main.EvSessionsFragment.Companion.TYPE_SUMMARY
import kotlinx.android.synthetic.main.session_header.view.*
import kotlinx.android.synthetic.main.session_item.view.*
import kotlinx.android.synthetic.main.session_item.view.session_item_date
import kotlinx.android.synthetic.main.session_item_total.view.*
import kotlinx.android.synthetic.main.summary_item.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class SessionsAdapter(val tags: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var sessions: List<ListItem> = emptyList()
    private var items: List<SummaryItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (tags) {
            TYPE_SESSIONS -> {
                when (viewType) {
                    ListItem.TYPE_GENERAL -> {
                        val v1: View = inflater.inflate(R.layout.session_item, parent,
                            false)
                        viewHolder = GeneralViewHolder(v1)
                    }
                    ListItem.TYPE_DATE -> {
                        val v2: View = inflater.inflate(R.layout.session_header, parent, false)
                        viewHolder = DateViewHolder(v2)
                    }
                    ListItem.TYPE_TOTAL -> {
                        val v3: View = inflater.inflate(R.layout.session_item_total, parent, false)
                        viewHolder = TotalViewHolder(v3)
                    }
                }
            }
            TYPE_SUMMARY -> {
                val view: View = inflater.inflate(R.layout.summary_item, parent, false)
                view.layoutParams.width = (parent.width * 0.4).toInt()
                viewHolder = SummaryViewHolder(view)
            }

        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (tags) {
            TYPE_SESSIONS -> {
                when (holder.itemViewType) {

                    ListItem.TYPE_GENERAL -> {
                        (holder as GeneralViewHolder).bind(position)
                    }
                    ListItem.TYPE_DATE -> {
                        (holder as DateViewHolder).bind(position)
                    }
                    ListItem.TYPE_TOTAL -> {
                        (holder as TotalViewHolder).bind(position)
                    }
                }
            }
            TYPE_SUMMARY -> (holder as SummaryViewHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(tags == TYPE_SESSIONS) sessions[position].type else 0
    }

    override fun getItemCount(): Int {
        return if (tags == TYPE_SESSIONS) sessions.size else items.size
    }

    fun setSessions(map: HashMap<String, List<Session>>) {
        // We linearly add every item into the consolidatedList.
        this.sessions = createListwithMapItems(map)
    }

    private fun createListwithMapItems(
        map: HashMap<String, List<Session>>,
    ): MutableList<ListItem> {
        val consolidatedList: MutableList<ListItem> = ArrayList()
        for (date in map.keys) {
            val dateItem = DateItem()
            dateItem.date = date
            consolidatedList.add(dateItem)
            var savingsTotal = 0.00
            var costTotal = 0.00
            for (item in map[date]!!) {
                val generalItem = GeneralItem()
                generalItem.session = item
                consolidatedList.add(generalItem)
                savingsTotal += item.saving
                costTotal += item.cost
            }

            val totalItem = TotalItem()
            totalItem.savings = savingsTotal
            totalItem.cost = costTotal
            consolidatedList.add(totalItem)
        }
        return consolidatedList
    }

    fun updateSessions(sessionsMap: HashMap<String, List<Session>>) {
        setSessions(sessionsMap)
        notifyDataSetChanged()
    }

    fun updateSummaryItems(sessionsMap: HashMap<String, List<Session>>) {
        setItems(sessionsMap)
        notifyDataSetChanged()
    }

    private fun setItems(map: HashMap<String, List<Session>>) {
        val list: ArrayList<SummaryItem> = ArrayList()
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
        list.add(SummaryItem("Total savings", formattedSavings))
        list.add(SummaryItem("Total cost", formattedCost))
        list.add(SummaryItem("Total charge duration", totalChargeDuration))
        this.items = list
    }

    inner class GeneralViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val dateView = item.session_item_date
        private val titleView = item.session_item_title
        private val savings = item.session_item_savings
        private val cost = item.session_item_cost
        //create an extension function on a date class which returns a string

        fun bind(position: Int) {
            val item = sessions[position] as GeneralItem

            val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

            val numberFormat = NumberFormat.getCurrencyInstance()
            val formattedSavings = numberFormat.format(item.session?.saving)
            val formattedCost = numberFormat.format(item.session?.cost)


            dateView.text = dateFormatter.parse(item.session?.date).dateToString("dd MMM")
            titleView.text = item.session?.address
            savings.text = formattedSavings
            cost.text = formattedCost
        }
    }

    inner class DateViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val dateView = item.session_header_date
        private val savingsView = item.session_header_savings
        private val costView = item.session_header_cost

        fun bind(position: Int) {
            val item = sessions[position] as DateItem
            dateView.text = item.date
            if (position == 0) {
                savingsView.visibility = View.VISIBLE
                costView.visibility = View.VISIBLE
            }
        }
    }

    inner class TotalViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val savingsView = item.total_savings
        private val costView = item.total_cost

        fun bind(position: Int) {
            val item = sessions[position] as TotalItem
            val numberFormat = NumberFormat.getCurrencyInstance()
            val formattedSavings = numberFormat.format(item.savings)
            val formattedCost = numberFormat.format(item.cost)
            savingsView.text = formattedSavings.toString()
            costView.text = formattedCost.toString()
        }
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
