package com.ticket.master.eventfinder.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.adapter.SearchResultRecyclerViewAdapter
import com.ticket.master.eventfinder.databinding.FragmentSearchResultBinding
import com.ticket.master.eventfinder.models.EventData

class SearchResultFragment : Fragment() {

    private lateinit var _binding: FragmentSearchResultBinding
    private val binding get() = _binding!!

    private lateinit var eventListAdapter: SearchResultRecyclerViewAdapter

    private val args : SearchResultFragmentArgs by navArgs()

//    private lateinit var viewModel : SearchResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this)[SearchResultViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchResultToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        //Set up the recycler view
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        eventListAdapter = SearchResultRecyclerViewAdapter(requireActivity().findNavController(R.id.fragmentContainerView))
        binding.eventListRecyclerView.adapter = eventListAdapter
        binding.eventListRecyclerView.layoutManager = LinearLayoutManager(activity)
//        var eventList: List<EventData> =
//            arrayListOf(
//                EventData(args.keyword, args.distance.toString(), args.location, "9:00 pm", args.category),
//                EventData("Test1", "11/12/2023", "Pune Stadium", "9:00 pm", "Music"),
//                EventData("Test1", "11/12/2023", "Pune Stadium", "9:00 pm", "Music"),
//                EventData("Test1", "11/12/2023", "Pune Stadium", "9:00 pm", "Music")
//            )
//        eventListAdapter.submitList(eventList)
//        viewModel.getEvents(10,"Default","Los Angeles")
//        viewModel.eventList.observe(viewLifecycleOwner){resource->
//            eventListAdapter.submitList(resource.body)
//        }
    }

}