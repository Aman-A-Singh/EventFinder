package com.ticket.master.eventfinder.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.firebase.geofire.core.GeoHash
import com.google.android.material.snackbar.Snackbar
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.databinding.FragmentSearchBinding
import com.ticket.master.eventfinder.util.Constants

class SearchFragment : Fragment() {

    private lateinit var _binding: FragmentSearchBinding
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchFragmentViewModel

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
        viewModel = ViewModelProvider(this)[SearchFragmentViewModel::class.java]

        initAutoSuggesstion()

        initSearchButtonClick()

        binding.clearBtn.setOnClickListener {
            clearEnteredData()
        }

        binding.autoLocationSwitch.setOnClickListener {
            setupAutoLocation()
        }

        val arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category,
            R.layout.custom_drop_down
        )
        binding.categorySpinner.adapter = arrayAdapter
    }

    private fun setupAutoLocation() {
        if (binding.autoLocationSwitch.isChecked) {
            binding.locationEdittxt.visibility = View.GONE
        } else {
            binding.locationEdittxt.visibility = View.VISIBLE
        }
    }

    private fun initSearchButtonClick() {
        binding.searchBtn.setOnClickListener {

            if (checkEnteredValues()) {
                val address: String?
                if (!binding.autoLocationSwitch.isChecked) {
                    address = binding.locationEdittxt.text.toString().trim()
                } else {
                    address = null
                }
                var bundle = getInfo()
                viewModel.getLocation(address)
                viewModel.location.observe(viewLifecycleOwner) { location ->
                    val geoHash = GeoHash(location.lat, location.lng, 7).geoHashString
                    bundle.putString(Constants.ARG_GEOHASH, geoHash)
                    this.findNavController()
                        .navigate(R.id.action_searchFragment2_to_searchResultFragment, bundle)
                }
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
    }

    private fun getInfo(): Bundle {
        val bundle = bundleOf(
            Constants.ARG_KEYWORD to binding.keywordEdittxt.text.toString().trim(),
            Constants.ARG_DISTANCE to binding.distanceEdittxt.text.trim().toString()
                .toInt()
        )
        var category = when (binding.categorySpinner.selectedItemPosition) {
            0 -> "Default"
            1 -> "music"
            2 -> "sports"
            3 -> "arts"
            4 -> "film"
            5 -> "misc"
            else ->
                "Default"
        }
        bundle.putString(Constants.ARG_CATEGORY, category)
        return bundle
    }

    private fun initAutoSuggesstion() {

        viewModel.stringList.observe(viewLifecycleOwner) {
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
            binding.keywordEdittxt.setAdapter(adapter)
        }

        binding.keywordEdittxt.threshold = 1

        binding.keywordEdittxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val enteredText = s.toString()
                if (s != null && s.length > 0) {
                    viewModel.getSuggestions(enteredText)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
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