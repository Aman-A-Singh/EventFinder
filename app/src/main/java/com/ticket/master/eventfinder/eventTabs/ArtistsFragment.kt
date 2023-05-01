package com.ticket.master.eventfinder.eventTabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ticket.master.eventfinder.databinding.FragmentArtistsBinding
import com.ticket.master.eventfinder.eventDetails.EventDetailsViewModel
import com.ticket.master.eventfinder.util.UIState

class ArtistsFragment : Fragment() {

    private lateinit var _binding: FragmentArtistsBinding
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<EventDetailsViewModel>()

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
                UIState.INPROGREES -> {}
                UIState.COMPLETED -> {
                    initUI()
                }
                UIState.ERROR -> {}
            }
        }
    }

    private fun initUI() {
        viewModel.eventData.let { eventDetails ->
            val performers = mutableListOf<String>()
            eventDetails._embedded.attractions?.map {
                performers.add(it.name)
            } ?: run{

            }
            if(performers.size>0){
                viewModel.getArtists(performers)
            }
        }
    }
}