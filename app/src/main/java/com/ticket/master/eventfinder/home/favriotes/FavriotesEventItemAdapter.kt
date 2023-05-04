package com.ticket.master.eventfinder.home.favriotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ticket.master.eventfinder.database.EventEntity
import com.ticket.master.eventfinder.databinding.EventItemRowBinding

class FavriotesEventItemAdapter(
    val navController: NavController,
    val dataBaseViewModel: FavoritesFragmentViewModel
) : ListAdapter<EventEntity, FavriotesEventItemViewHolder>(
    FavriotesEventDiffCallBack()
) {
    lateinit var binding:EventItemRowBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavriotesEventItemViewHolder {
        binding =
            EventItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.eventTitle.isSelected = true
        binding.locationTxt.isSelected = true
        return FavriotesEventItemViewHolder(binding, navController, dataBaseViewModel)
    }

    override fun onBindViewHolder(holder: FavriotesEventItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun deleteItem(position: Int,viewHolder: RecyclerView.ViewHolder) {
        val event =getItem(position)
        var snackbar =
            Snackbar.make(
                viewHolder.itemView,
                event.name + " removed from favriotes",
                Snackbar.LENGTH_LONG
            )
        snackbar.show()
        dataBaseViewModel.removeEvent(event)
    }
}