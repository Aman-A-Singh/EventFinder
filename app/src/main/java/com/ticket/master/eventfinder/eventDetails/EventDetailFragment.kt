package com.ticket.master.eventfinder.eventDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.adapter.FragmentItems
import com.ticket.master.eventfinder.adapter.ViewPagerAdapter
import com.ticket.master.eventfinder.databinding.FragmentEventDetailBinding
import com.ticket.master.eventfinder.eventTabs.ArtistsFragment
import com.ticket.master.eventfinder.eventTabs.DetailsFragment
import com.ticket.master.eventfinder.eventTabs.VenueFragment
import com.ticket.master.eventfinder.util.UIState

class EventDetailFragment : Fragment() {

    private lateinit var _binding: FragmentEventDetailBinding
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<EventDetailsViewModel>()

    private val args: EventDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {_binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventDetailsToolBar.inflateMenu(R.menu.event_details_menu)
        viewModel.uiState.value = UIState.INPROGREES
        viewModel.getEventData(args.eventId)
        viewModel.isFavriote.value = args.isFavorite
        binding.eventDetailsToolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        setUpTab()
        setUpTabIcons()
    }

    private fun setUpTabIcons() {
        binding.eventDetailsTab.getTabAt(0)?.setIcon(R.drawable.ic_details)
        binding.eventDetailsTab.getTabAt(1)?.setIcon(R.drawable.artist_icon)
        binding.eventDetailsTab.getTabAt(2)?.setIcon(R.drawable.ic_venue)
    }

    private fun setUpTab() {
        val fragmentItems: MutableList<FragmentItems> = arrayListOf()
        fragmentItems.add(
            FragmentItems(DetailsFragment(), "DETAILS")
        )
        fragmentItems.add(
            FragmentItems(ArtistsFragment(), "ARTIST(S)")
        )
        fragmentItems.add(
            FragmentItems(VenueFragment(), "VENUE")
        )
        val adapter = ViewPagerAdapter(childFragmentManager, fragmentItems)
        binding.eventDetailsViewPager.adapter = adapter
        binding.eventDetailsTab.setupWithViewPager(binding.eventDetailsViewPager)
    }
}