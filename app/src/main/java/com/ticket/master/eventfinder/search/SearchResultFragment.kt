package com.ticket.master.eventfinder.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.geofire.core.GeoHash
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.adapter.SearchResultRecyclerViewAdapter
import com.ticket.master.eventfinder.database.DataBaseViewModel
import com.ticket.master.eventfinder.databinding.FragmentSearchResultBinding
import com.ticket.master.eventfinder.home.favriotes.FavoritesFragmentViewModel
import com.ticket.master.eventfinder.util.UIState

class SearchResultFragment : Fragment() {

    private lateinit var viewModel: SearchResultFragmentViewModel
    private lateinit var _binding: FragmentSearchResultBinding
    private val binding get() = _binding!!

    private lateinit var eventListAdapter: SearchResultRecyclerViewAdapter
    private val sharedViewModel by activityViewModels<FavoritesFragmentViewModel>()

    private val args: SearchResultFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchResultFragmentViewModel::class.java]
        viewModel.getLocation(args.address)
//        sharedViewModel = ViewModelProvider(this)[FavoritesFragmentViewModel::class.java]
        binding.searchResultToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                UIState.INPROGREES -> {
                    binding.noEventInfo.visibility = View.GONE
                    binding.intermediateProgressBar.visibility = View.VISIBLE
                }
                UIState.COMPLETED -> {
                    binding.intermediateProgressBar.visibility = View.GONE
                }

                UIState.ERROR -> {
                    binding.intermediateProgressBar.visibility = View.GONE
                    binding.noEventInfo.visibility = View.VISIBLE
                }
            }
        }
        //Set up the recycler view
        configureRecyclerView()

        sharedViewModel.favoritesEventList.observe(viewLifecycleOwner){
            configureRecyclerView()
        }
    }

    private fun configureRecyclerView() {
        eventListAdapter =
            SearchResultRecyclerViewAdapter(requireActivity().findNavController(R.id.fragmentContainerView),sharedViewModel)
        binding.eventListRecyclerView.adapter = eventListAdapter
        binding.eventListRecyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.location.observe(viewLifecycleOwner){location->
            val geoHash = GeoHash(location.lat, location.lng, 7).geoHashString
            viewModel.getEvents(args.distance, args.category, args.keyword, geoHash)
        }

        viewModel.eventList.observe(viewLifecycleOwner) { eventList ->
            eventListAdapter.submitList(eventList)
        }
    }
}