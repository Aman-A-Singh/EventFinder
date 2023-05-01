package com.ticket.master.eventfinder.common

import androidx.recyclerview.widget.DiffUtil
import com.ticket.master.eventfinder.models.artist.ArtistDetail
import com.ticket.master.eventfinder.models.event.EventItem

class ArtistDiffCallBack: DiffUtil.ItemCallback<ArtistDetail>(){
    override fun areItemsTheSame(oldItem: ArtistDetail, newItem: ArtistDetail): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArtistDetail, newItem: ArtistDetail): Boolean {
        return oldItem==newItem
    }
}