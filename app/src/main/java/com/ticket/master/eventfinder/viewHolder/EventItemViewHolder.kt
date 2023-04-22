package com.ticket.master.eventfinder.viewHolder

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.databinding.EventItemRowBinding
import com.ticket.master.eventfinder.models.EventData


class EventItemViewHolder(
    private val itemBinding: EventItemRowBinding,
    private val navController: NavController
) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(eventData: EventData) {
        itemBinding.eventTitle.text = eventData.eventName
        itemBinding.date.text = eventData.eventDate
        itemBinding.timeTxt.text = eventData.eventTime
        itemBinding.locationTxt.text = eventData.eventLocation
        itemBinding.eventType.text = eventData.eventType

        itemView.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_eventDetailFragment)
        }

        itemBinding.addFavoritesImage.setOnClickListener {
            itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_filled)
        }
    }
}