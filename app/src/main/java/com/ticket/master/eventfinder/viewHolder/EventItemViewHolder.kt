package com.ticket.master.eventfinder.viewHolder

import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.database.DataBaseViewModel
import com.ticket.master.eventfinder.database.EventEntity
import com.ticket.master.eventfinder.databinding.EventItemRowBinding
import com.ticket.master.eventfinder.home.favriotes.FavoritesFragmentViewModel
import com.ticket.master.eventfinder.models.event.EventItem
import com.ticket.master.eventfinder.util.Constants
import java.text.SimpleDateFormat


class EventItemViewHolder(
    private val itemBinding: EventItemRowBinding,
    private val navController: NavController,
    val dataBaseViewModel: FavoritesFragmentViewModel
) :
    RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(eventData: EventItem) {
        itemBinding.eventTitle.text = eventData.name
        val dateFormat = SimpleDateFormat("MM/dd/yyyy")
        val date = dateFormat.format(eventData.dates.start.date)
        itemBinding.date.text = date
        val timeFormat = SimpleDateFormat("h:mm a")
        val time = timeFormat.format(eventData.dates.start.time)
        itemBinding.timeTxt.text = time
        itemBinding.locationTxt.text = eventData._embedded.venues[0].name
        itemBinding.eventType.text = eventData.classifications[0].segment.name

        Glide.with(itemBinding.eventImage.context)
            .load(eventData.images[6].url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemBinding.eventImage)

        itemView.setOnClickListener {
            var isFavorite = false
            if (dataBaseViewModel.isFavorite(eventData.id)) {
                isFavorite = true
            }
            val bundle = bundleOf(
                Constants.ARG_EVENT_ID to eventData.id,
                Constants.ARG_IS_FAVRIOTE to isFavorite
            )
            navController.navigate(R.id.action_homeFragment_to_eventDetailFragment, bundle)
        }

        if (dataBaseViewModel.isFavorite(eventData.id)) {
            itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_filled)
        }
        itemBinding.addFavoritesImage.setOnClickListener {
            val eventEntity = EventEntity(
                eventData.id,
                eventData.name,
                date,
                time,
                eventData._embedded.venues[0].name,
                eventData.classifications[0].segment.name,
                eventData.images[6].url
            )
            if (dataBaseViewModel.isFavorite(eventData.id)) {
                itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_outline)
                dataBaseViewModel.removeEvent(eventEntity)
                var snackbar =
                    Snackbar.make(
                        itemView,
                        eventData.name + " removed from favriotes",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()
            } else {
                itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_filled)
                dataBaseViewModel.insert(eventEntity)
                var snackbar =
                    Snackbar.make(
                        itemView,
                        eventData.name + " added to favriotes",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()
            }
        }
    }
}