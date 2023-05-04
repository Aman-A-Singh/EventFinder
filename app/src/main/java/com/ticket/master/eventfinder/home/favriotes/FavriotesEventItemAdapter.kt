package com.ticket.master.eventfinder.home.favriotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import com.ticket.master.eventfinder.database.EventEntity
import com.ticket.master.eventfinder.databinding.EventItemRowBinding

class FavriotesEventItemAdapter(
    val navController: NavController,
    val dataBaseViewModel: FavoritesFragmentViewModel
) : ListAdapter<EventEntity, FavriotesEventItemViewHolder>(
    FavriotesEventDiffCallBack()
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavriotesEventItemViewHolder {
        val binding =
            EventItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.eventTitle.isSelected = true
        binding.locationTxt.isSelected = true
        return FavriotesEventItemViewHolder(binding, navController, dataBaseViewModel)
    }

    override fun onBindViewHolder(holder: FavriotesEventItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}