package com.ticket.master.eventfinder.viewHolder

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.databinding.EventItemRowBinding
import com.ticket.master.eventfinder.models.EventData
import com.ticket.master.eventfinder.models.event.EventItem
import java.text.SimpleDateFormat


class EventItemViewHolder(
    private val itemBinding: EventItemRowBinding,
    private val navController: NavController
) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(eventData: EventItem) {
        itemBinding.eventTitle.text = eventData.name
//        val dateFormat = SimpleDateFormat("dd/mm/yyyy")
        itemBinding.date.text = eventData.dates.start.localDate
        itemBinding.timeTxt.text = eventData.dates.start.localTime
        itemBinding.locationTxt.text = eventData._embedded.venues[0].name
        itemBinding.eventType.text = eventData.classifications[0].segment.name

        itemView.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_eventDetailFragment)
        }

        itemBinding.addFavoritesImage.setOnClickListener {
            itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_filled)
        }
    }
}