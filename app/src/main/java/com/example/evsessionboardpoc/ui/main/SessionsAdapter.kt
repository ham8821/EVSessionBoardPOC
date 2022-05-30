package com.example.evsessionboardpoc.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evsessionboardpoc.R
import com.example.evsessionboardpoc.data.model.*
import kotlinx.android.synthetic.main.session_header.view.*
import kotlinx.android.synthetic.main.session_item.view.*
import kotlinx.android.synthetic.main.session_item.view.session_item_date
import kotlinx.android.synthetic.main.session_item_total.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class SessionsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var sessions: List<ListItem> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
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
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

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

    override fun getItemViewType(position: Int): Int {
        return sessions[position].type
    }

    override fun getItemCount(): Int {
        return if (sessions != null) sessions.size else 0
    }

    fun setSessions(sessions: List<Session>) {

        val list = sessions.groupDataIntoHashMap()

        // We linearly add every item into the consolidatedList.
        // We linearly add every item into the consolidatedList.
        val consolidatedList: MutableList<ListItem> = ArrayList()

        for (date in list.keys) {
            val dateItem = DateItem()
            dateItem.date = date
            consolidatedList.add(dateItem)
            var savingsTotal = 0.00
            var costTotal = 0.00
            for (item in list[date]!!) {
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

        this.sessions = consolidatedList
    }

    fun updateSessions(sessions: List<Session>) {
        setSessions(sessions)
        notifyDataSetChanged()
    }

    fun List<Session>.groupDataIntoHashMap(): HashMap<String, List<Session>> {
        val groupedHashMap: HashMap<String, List<Session>> = HashMap()

        for (session in this) {
            val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val hashMapKey: String = dateFormatter.parse(session.date).dateToString("MMMM YYYY")
            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.

                val list = groupedHashMap[hashMapKey]?.toMutableList()
                list?.add(session)
                groupedHashMap[hashMapKey] = list as List<Session>
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                val list: MutableList<Session> = ArrayList()
                list.add(session)
                groupedHashMap[hashMapKey] = list
            }
        }

        return groupedHashMap
    }

    fun Date.dateToString(format: String): String {
        //simple date formatter
        val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
        //return the formatted date string
        return dateFormatter.format(this)
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
}
