package com.ticket.master.eventfinder.eventTabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.adapter.ArtistRecyclerViewAdapter
import com.ticket.master.eventfinder.adapter.SearchResultRecyclerViewAdapter
import com.ticket.master.eventfinder.databinding.EmptyViewBinding
import com.ticket.master.eventfinder.databinding.FragmentArtistsBinding
import com.ticket.master.eventfinder.eventDetails.EventDetailsViewModel
import com.ticket.master.eventfinder.util.UIState

class ArtistsFragment : Fragment() {

    private lateinit var _binding: FragmentArtistsBinding
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<EventDetailsViewModel>()
    private lateinit var emptyViewBinding: EmptyViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                UIState.INPROGREES -> {
                    binding.noArtistInfo.visibility = View.GONE
                    binding.intermediateProgressBar.visibility = View.VISIBLE
                }

                UIState.COMPLETED -> {
                    binding.intermediateProgressBar.visibility = View.GONE
                    initUI()
                }

                UIState.ERROR -> {
                    binding.noArtistInfo.visibility = View.VISIBLE
                }
            }
        }
        configureRecyclerView()
    }

    private fun initUI() {
        viewModel.eventData.let { eventDetails ->
            val performers = mutableListOf<String>()
            eventDetails._embedded.attractions?.map {
                performers.add(it.name)
            } ?: run {

            }
            if (performers.size > 0) {
                viewModel.getArtists(performers)
            }
        }
    }

    private fun configureRecyclerView() {
        var artistListAdapter = ArtistRecyclerViewAdapter()
        binding.artistRecyclerView.adapter = artistListAdapter
        binding.artistRecyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.artistList.observe(viewLifecycleOwner) { eventList ->
            if (eventList.size > 0) {
                binding.noArtistInfo.visibility = View.GONE
                artistListAdapter.submitList(eventList)
            } else {
                binding.noArtistInfo.visibility = View.VISIBLE
            }
        }
    }
}