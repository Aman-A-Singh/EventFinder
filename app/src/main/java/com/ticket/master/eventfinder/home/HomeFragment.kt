package com.ticket.master.eventfinder.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ticket.master.eventfinder.adapter.FragmentItems
import com.ticket.master.eventfinder.adapter.ViewPagerAdapter
import com.ticket.master.eventfinder.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTab()
    }

    private fun setUpTab() {
        val fragmentItems: MutableList<FragmentItems> = arrayListOf()
        fragmentItems.add(
            FragmentItems(SearchTabScreen(), "SEARCH")
        )
        fragmentItems.add(
            FragmentItems(FavoritesFragment(), "FAVORITES")
        )
        val adapter = ViewPagerAdapter(childFragmentManager, fragmentItems)

        binding.homeViewPager.adapter = adapter
        binding.homeTab.setupWithViewPager(binding.homeViewPager)
    }
}