package com.ticket.master.eventfinder.home.favriotes

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.database.EventEntity
import com.ticket.master.eventfinder.databinding.EventItemRowBinding
import com.ticket.master.eventfinder.util.Constants

class FavriotesEventItemViewHolder(

    private val itemBinding: EventItemRowBinding,
    private val navController: NavController,
    val viewModel: FavoritesFragmentViewModel
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(eventEntity: EventEntity) {
        itemBinding.eventTitle.text = eventEntity.name
        itemBinding.date.text = eventEntity.date
        itemBinding.timeTxt.text = eventEntity.time
        itemBinding.locationTxt.text = eventEntity.venue
        itemBinding.eventType.text = eventEntity.type

        Glide.with(itemBinding.eventImage.context)
            .load(eventEntity.url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(itemBinding.eventImage)

        itemView.setOnClickListener {
            val bundle = bundleOf(Constants.ARG_EVENT_ID to eventEntity.id)
            navController.navigate(R.id.action_homeFragment_to_eventDetailFragment, bundle)
        }


        itemBinding.addFavoritesImage.setImageResource(R.drawable.heart_filled)
        itemBinding.addFavoritesImage.setOnClickListener {
            viewModel.removeEvent(eventEntity)
        }
    }
}