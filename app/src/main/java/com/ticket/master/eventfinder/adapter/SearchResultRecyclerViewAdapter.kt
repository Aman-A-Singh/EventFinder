package com.ticket.master.eventfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import com.ticket.master.eventfinder.common.EventDiffCallBack
import com.ticket.master.eventfinder.databinding.EventItemRowBinding
import com.ticket.master.eventfinder.models.EventData
import com.ticket.master.eventfinder.viewHolder.EventItemViewHolder

class SearchResultRecyclerViewAdapter(val navController: NavController) : ListAdapter<EventData, EventItemViewHolder>(
    EventDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventItemViewHolder {
        val binding = EventItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return EventItemViewHolder(binding,navController)
    }

    override fun onBindViewHolder(holder: EventItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}