package com.ticket.master.eventfinder.home.favriotes

import androidx.recyclerview.widget.DiffUtil
import com.ticket.master.eventfinder.database.EventEntity
import com.ticket.master.eventfinder.models.event.EventItem

class FavriotesEventDiffCallBack : DiffUtil.ItemCallback<EventEntity>() {
    override fun areItemsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EventEntity, newItem: EventEntity): Boolean {
        return oldItem == newItem
    }
}