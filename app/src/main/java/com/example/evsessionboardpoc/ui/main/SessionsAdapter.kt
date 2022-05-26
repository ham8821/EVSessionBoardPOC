package com.example.evsessionboardpoc.ui.main

import android.icu.text.NumberFormat.CURRENCYSTYLE
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evsessionboardpoc.R
import com.example.evsessionboardpoc.data.model.Session
import kotlinx.android.synthetic.main.session_item.view.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class SessionsAdapter : RecyclerView.Adapter<SessionsAdapter.SessionViewHolder>(){

    private var sessions: List<Session> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.session_item, parent, false)
        return SessionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = sessions.size

    fun setSessions(sessions: List<Session>) {
        this.sessions = sessions
    }

    fun updateSessions(sessions: List<Session>) {
        setSessions(sessions)
        notifyDataSetChanged()
    }

    inner class SessionViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val dateView = item.session_item_date
        private val titleView = item.session_item_title
        private val savings = item.session_item_savings
        private val cost = item.session_item_cost
        //create an extension function on a date class which returns a string
        private fun Date.dateToString(format: String): String {
            //simple date formatter
            val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
            //return the formatted date string
            return dateFormatter.format(this)
        }

        fun bind(position: Int) {
            val session = sessions[position]


            val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

            val numberFormat = NumberFormat.getCurrencyInstance()
//            numberFormat.maximumFractionDigits = 0;
            val formattedSavings = numberFormat.format(session.saving)
            val formattedCost = numberFormat.format(session.cost)


            dateView.text = dateFormatter.parse(session.date).dateToString("dd MMM")
            titleView.text = session.address
            savings.text = formattedSavings
            cost.text = formattedCost
        }
    }
}