package com.ticket.master.eventfinder.home.favriotes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.common.SwipeToDeleteCallback
import com.ticket.master.eventfinder.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<FavoritesFragmentViewModel>()
    private lateinit var eventListAdapter: FavriotesEventItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        eventListAdapter =
            FavriotesEventItemAdapter(
                requireActivity().findNavController(R.id.fragmentContainerView),
                viewModel
            )
        binding.favorioteEventListRecyclerView.adapter = eventListAdapter
        binding.favorioteEventListRecyclerView.layoutManager = LinearLayoutManager(activity)
        viewModel.favoritesEventList.observe(viewLifecycleOwner) { eventList ->
            eventListAdapter.submitList(eventList)
            if (eventList.size == 0) {
                binding.noFavoritesInfo.visibility = View.VISIBLE
            }else{
                binding.noFavoritesInfo.visibility = View.GONE
            }
        }
        val swipeToDeleteCallback = SwipeToDeleteCallback(eventListAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.favorioteEventListRecyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}