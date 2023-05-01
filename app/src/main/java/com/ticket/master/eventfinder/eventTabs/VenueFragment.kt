package com.ticket.master.eventfinder.eventTabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ticket.master.eventfinder.databinding.FragmentVenueBinding
import com.ticket.master.eventfinder.eventDetails.EventDetailsViewModel
import com.ticket.master.eventfinder.models.event.EventDetails
import com.ticket.master.eventfinder.models.location.LocationX
import com.ticket.master.eventfinder.util.Constants.MAPVIEW_BUNDLE_KEY
import com.ticket.master.eventfinder.util.UIState


class VenueFragment : Fragment(), OnMapReadyCallback {

    private lateinit var _binding: FragmentVenueBinding
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<EventDetailsViewModel>()

    private lateinit var googleMap: GoogleMap
    private lateinit var location: LatLng

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVenueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                UIState.INPROGREES -> binding.venuCardView.visibility = View.GONE
                UIState.COMPLETED -> {
                    binding.venuCardView.visibility = View.VISIBLE
                    bind()
                }

                UIState.ERROR -> binding.venuCardView.visibility = View.VISIBLE
            }
        }
        binding.mapView.onCreate(mapViewBundle)
        binding.mapView.getMapAsync(this)
    }

    private fun bind() {
        viewModel.eventData.let {
            binding.venueName.isSelected = true
            binding.addressTxt.isSelected = true

            binding.venueName.text = it.name

            var venue = it._embedded.venues[0]
            var addressText: String? = null
            if (venue.address != null) {
                addressText = venue.address!!.line1
            }
            if (venue.city != null) {
                addressText = addressText + ", " + venue.city!!.name
            }
            if (venue.state != null) {
                addressText = addressText + ", " + venue.state!!.name
            }
            binding.addressTxt.text = addressText

            val _location = it._embedded.venues[0].location
            if (_location != null) {
                location = LatLng(_location.latitude.toDouble(), _location.longitude.toDouble())
            }
            bindMap()
        }
    }

    private fun bindMap() {
        if (::googleMap.isInitialized && ::location.isInitialized) {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(location))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
//                binding.mapCardView.visibility = View.GONE
        }
    }

    override fun onMapReady(map: GoogleMap) {
        // Save reference to GoogleMap object
        googleMap = map
        bindMap()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        binding.mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}