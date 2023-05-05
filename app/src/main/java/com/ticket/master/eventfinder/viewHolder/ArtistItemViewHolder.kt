package com.ticket.master.eventfinder.viewHolder

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ticket.master.eventfinder.databinding.ArtistItemRowBinding
import com.ticket.master.eventfinder.models.artist.Album
import com.ticket.master.eventfinder.models.artist.ArtistDetail
import java.text.NumberFormat
import java.util.Locale

class ArtistItemViewHolder(private val itemBinding: ArtistItemRowBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun shorthand(number: Double): String {
        val suffixes = arrayOf("", "K", "M", "B", "T") // Define the suffixes
        var value = number
        var index = 0
        while (value >= 1000 && index < suffixes.size - 1) { // Loop through the suffixes
            value /= 1000
            index++
        }
        return "%.0f%s".format(value, suffixes[index]) // Return the shorthand expression
    }


    fun bind(item: ArtistDetail) {
        itemBinding.artistName.text = item.name
        itemBinding.spotifyLink.paintFlags = Paint.UNDERLINE_TEXT_FLAG


        val number = item.followers.total.toDouble()
        val myString = shorthand(number)

        itemBinding.followers.text = myString
        Log.d("Followers", myString)

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

        itemBinding.popularityProgress.progress = item.popularity
        itemBinding.popularityValueTxt.text = item.popularity.toString()

        configureAlbum(item.albums)
    }

    private fun configureAlbum(albums: List<Album>) {
        if (albums.size != 0) {
            Glide.with(itemBinding.album1Image.context)
                .load(albums[0].images[0].url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemBinding.album1Image)
            Glide.with(itemBinding.album2Image.context)
                .load(albums[1].images[0].url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemBinding.album2Image)
            Glide.with(itemBinding.album3Image.context)
                .load(albums[2].images[0].url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemBinding.album3Image)
        } else {
            itemBinding.albumCardView.visibility = View.GONE
        }
    }
}