package com.ticket.master.eventfinder.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.databinding.FragmentSearchBinding
import com.ticket.master.eventfinder.home.HomeFragment
import com.ticket.master.eventfinder.home.SearchTabScreen
import com.ticket.master.eventfinder.util.Constants

class SearchFragment : Fragment() {

    private lateinit var _binding: FragmentSearchBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBtn.setOnClickListener {
            if (checkEnteredValues()) {
                val location: String
                if (!binding.autoLocationSwitch.isChecked) {
                    location = binding.locationEdittxt.text.toString().trim()
                } else {
                    location = "Auto Detect"
                }
                val bundle = bundleOf(
                    Constants.ARG_KEYWORD to binding.keywordEdittxt.text.toString().trim(),
                    Constants.ARG_DISTANCE to binding.distanceEdittxt.text.trim().toString().toInt(),
                    Constants.ARG_CATEGORY to binding.categorySpinner.selectedItem.toString().trim(),
                    Constants.ARG_LOCATION to location
                )
                this.findNavController()
                    .navigate(R.id.action_searchFragment2_to_searchResultFragment, bundle)
            } else {
                var snackbar =
                    Snackbar.make(
                        binding.searchBtn,
                        "Please fill all fields",
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()
            }
        }

        binding.clearBtn.setOnClickListener {
            clearEnteredData()
        }

        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category,
            R.layout.custom_drop_down
        )
        binding.categorySpinner.adapter = arrayAdapter
    }

    private fun clearEnteredData() {
        binding.keywordEdittxt.text.clear()
        binding.distanceEdittxt.text.clear()
        binding.categorySpinner.setSelection(0)
        binding.locationEdittxt.text.clear()
        if (binding.autoLocationSwitch.isChecked) {
            binding.autoLocationSwitch.toggle()
        }
    }

    private fun checkEnteredValues(): Boolean {
        return !binding.keywordEdittxt.text.isEmpty() &&
                !binding.distanceEdittxt.text.isEmpty() &&
                if (binding.autoLocationSwitch.isChecked)
                    true
                else !binding.locationEdittxt.text.isEmpty()
    }
}