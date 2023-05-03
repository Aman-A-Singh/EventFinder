package com.ticket.master.eventfinder.viewHolder

import android.content.Context
import android.util.Log
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.database.DataBaseViewModel
import com.ticket.master.eventfinder.database.EventEntity
import com.ticket.master.eventfinder.database.EventEntityRepository
import com.ticket.master.eventfinder.databinding.EventItemRowBinding
import com.ticket.master.eventfinder.models.event.EventItem
import com.ticket.master.eventfinder.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class EventItemViewHolder(
    private val itemBinding: EventItemRowBinding,
    private val navController: NavController,
    val dataBaseViewModel: DataBaseViewModel
) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(eventData: EventItem) {
        itemBinding.eventTitle.text = eventData.name
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
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
            navController.navigate(R.id.action_homeFragment_to_eventDetailFragment, bundle)
        }

        if (dataBaseViewModel.isFavorite(eventData.id)) {
            itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_filled)
        }
        itemBinding.addFavoritesImage.setOnClickListener {
            if (dataBaseViewModel.isFavorite(eventData.id)) {
                itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_outline)
                dataBaseViewModel.removeEvent(EventEntity(eventData.id))
            } else {
                itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_filled)
                dataBaseViewModel.insert(EventEntity(eventData.id))
            }
        }
    }
}