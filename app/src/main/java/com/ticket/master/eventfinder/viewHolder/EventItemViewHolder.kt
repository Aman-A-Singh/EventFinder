package com.ticket.master.eventfinder.viewHolder

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.databinding.EventItemRowBinding
import com.ticket.master.eventfinder.models.event.EventItem
import com.ticket.master.eventfinder.util.Constants
import java.text.SimpleDateFormat


class EventItemViewHolder(
    private val itemBinding: EventItemRowBinding,
    private val navController: NavController
) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(eventData: EventItem) {
        itemBinding.eventTitle.text = eventData.name
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        itemBinding.date.text = dateFormat.format(eventData.dates.start.date)
        val timeFormat = SimpleDateFormat("h:mm a")
        itemBinding.timeTxt.text = timeFormat.format(eventData.dates.start.time)
        itemBinding.locationTxt.text = eventData._embedded.venues[0].name
        itemBinding.eventType.text = eventData.classifications[0].segment.name

        Glide.with(itemBinding.eventImage.context)
            .load(eventData.images[6].url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemBinding.eventImage)

        itemView.setOnClickListener {
            val bundle = bundleOf(Constants.ARG_EVENT_ID to eventData.id)
            navController.navigate(R.id.action_homeFragment_to_eventDetailFragment,bundle)
        }

        itemBinding.addFavoritesImage.setOnClickListener {
            itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_filled)
        }
    }
}