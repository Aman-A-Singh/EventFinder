package com.ticket.master.eventfinder.viewHolder

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ticket.master.eventfinder.databinding.ArtistItemRowBinding
import com.ticket.master.eventfinder.models.artist.ArtistDetail

class ArtistItemViewHolder(private val itemBinding: ArtistItemRowBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: ArtistDetail) {
        itemBinding.artistName.text = item.name

        Glide.with(itemBinding.artistImage.context)
            .load(item.images[0].url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemBinding.artistImage)

        itemBinding.spotifyLink.text = "Check out on Spotify"
        itemBinding.spotifyLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(item.external_urls.spotify)
            itemView.context.startActivity(intent)
        }
    }
}