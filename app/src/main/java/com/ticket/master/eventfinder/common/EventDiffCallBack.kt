package com.ticket.master.eventfinder.common

import androidx.recyclerview.widget.DiffUtil
import com.ticket.master.eventfinder.models.EventData

class EventDiffCallBack:DiffUtil.ItemCallback<EventData>(){
    override fun areItemsTheSame(oldItem: EventData, newItem: EventData): Boolean {
        return oldItem.eventName == newItem.eventName
    }

    override fun areContentsTheSame(oldItem: EventData, newItem: EventData): Boolean {
        return oldItem==newItem
    }
}