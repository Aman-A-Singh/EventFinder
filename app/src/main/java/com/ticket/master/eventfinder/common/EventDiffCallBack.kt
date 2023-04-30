package com.ticket.master.eventfinder.common

import androidx.recyclerview.widget.DiffUtil
import com.ticket.master.eventfinder.models.event.EventItem

class EventDiffCallBack:DiffUtil.ItemCallback<EventItem>(){
    override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
        return oldItem==newItem
    }
}