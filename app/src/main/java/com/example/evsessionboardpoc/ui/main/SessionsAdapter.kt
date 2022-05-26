package com.example.evsessionboardpoc.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.evsessionboardpoc.R
import com.example.evsessionboardpoc.data.model.Session
import kotlinx.android.synthetic.main.session_item.view.*

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

    fun setSessions(movies: List<Session>) {
        this.sessions = movies
    }

    fun updateSessions(movies: List<Session>) {
        setSessions(movies)
        notifyDataSetChanged()
    }

    inner class SessionViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val titleView = item.session_item_title

        fun bind(position: Int) {
            val movie = sessions[position]

            titleView.text = movie.address
        }
    }
}