package com.ticket.master.eventfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ticket.master.eventfinder.common.ArtistDiffCallBack
import com.ticket.master.eventfinder.databinding.ArtistItemRowBinding
import com.ticket.master.eventfinder.models.artist.ArtistDetail
import com.ticket.master.eventfinder.viewHolder.ArtistItemViewHolder

class ArtistRecyclerViewAdapter :
    ListAdapter<ArtistDetail, ArtistItemViewHolder>(ArtistDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistItemViewHolder {
        val binding = ArtistItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtistItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}