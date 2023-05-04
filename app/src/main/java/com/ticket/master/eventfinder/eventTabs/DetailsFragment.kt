package com.ticket.master.eventfinder.eventTabs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.ticket.master.eventfinder.R
import com.ticket.master.eventfinder.database.DataBaseViewModel
import com.ticket.master.eventfinder.database.EventEntity
import com.ticket.master.eventfinder.databinding.FragmentDetailsBinding
import com.ticket.master.eventfinder.eventDetails.EventDetailsViewModel
import com.ticket.master.eventfinder.models.event.EventDetails
import com.ticket.master.eventfinder.util.UIState
import java.text.SimpleDateFormat
import javax.sql.DataSource

class DetailsFragment : Fragment() {

    private lateinit var _binding: FragmentDetailsBinding
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<EventDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.artistTeamsTxt.isSelected = true
        binding.buyTicketAtText.isSelected = true
        binding.buyTicketAtText.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                UIState.INPROGREES -> {
                    binding.eventDetailsCardView.visibility = View.GONE
                    binding.intermediateProgressBar.visibility = View.VISIBLE
                }

                UIState.COMPLETED -> {
                    binding.eventDetailsCardView.visibility = View.VISIBLE
                    binding.intermediateProgressBar.visibility = View.GONE
                    bind()
                }

                UIState.ERROR -> {}
            }
        }
    }

    private fun bind() {
        viewModel.eventData.let { event ->
            binding.artistTeamsTxt.text = event.name
            val venue = event._embedded.venues[0].name
            binding.venueTxt.text = venue
            val dateFormat = SimpleDateFormat("MMM dd,yyyy")
            val date = dateFormat.format(event.dates.start.date)
            binding.dateTxt.text = date
            val timeFormat = SimpleDateFormat("h:mm a")
            val time = timeFormat.format(event.dates.start.time)
            binding.timeTxt.text = time

            bindGenre(event.classifications)

            bindBuyTicketURL(event.url)

            if (event.priceRanges != null) {
                val min_val = event.priceRanges[0].min.toInt()
                val max_val = event.priceRanges[0].max.toInt()

                binding.priceRangeTxt.text =
                    min_val.toString() + " - " + max_val.toString() + " (" + event.priceRanges[0].currency + ")"
            } else {
                binding.priceRangeTxt.visibility = View.GONE
                binding.priceRange.visibility = View.GONE
            }
            bindSeatImage(event.seatmap.staticUrl)

            bindTicketStatus(event.dates.status.code)

            val eventEntity = EventEntity(
                event.id,
                event.name,
                date,
                time,
                venue,
                event.type,
                event.images[6].url
            )
            bindToolBar(eventEntity, event.url)
        }
    }

    private fun bindSeatImage(staticUrl: String) {
        Glide.with(binding.seatImage.context)
            .load(staticUrl)
            //                .placeholder(R.drawable.placeholder_image) // Placeholder image to display while loading the actual image
            //                .error(R.drawable.error_image) // Error image to display in case of loading failure
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.seatImage.visibility =
                        View.GONE // Hide the ImageView if the image is not available or loading fails
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.seatImage.visibility =
                        View.VISIBLE // Show the ImageView if the image is loaded successfully
                    return false
                }

            })
            .into(binding.seatImage)
    }

    private fun bindGenre(classifications: List<EventDetails.Classification>) {
        var genre = ArrayList<String>()
        if (classifications[0].segment != null) {
            if (!classifications[0].segment!!.name.equals("Undefined")) {
                genre.add(classifications[0].segment!!.name)
            }
        }
        if (classifications[0].genre != null) {
            if (!classifications[0].genre!!.name.equals("Undefined")) {
                genre.add(classifications[0].genre!!.name)
            }
        }
        if (classifications[0].subGenre != null) {
            if (!classifications[0].subGenre!!.name.equals("Undefined")) {
                genre.add(classifications[0].subGenre!!.name)
            }
        }
        if (classifications[0].type != null) {
            if (!classifications[0].type!!.name.equals("Undefined")) {
                genre.add(classifications[0].type!!.name)
            }
        }
        if ((classifications[0].subType != null)) {
            if (!classifications[0].subType!!.name.equals("Undefined")) {
                genre.add(classifications[0].subType!!.name)
            }
        }
        binding.genresTxt.isSelected = true
        binding.genresTxt.text = genre.joinToString(" | ")
    }

    private fun bindToolBar(eventEntity: EventEntity, url: String?) {
        val toolbarTitle =
            requireParentFragment().view?.findViewById<TextView>(R.id.toolBar_title)
        toolbarTitle?.isSelected = true
        toolbarTitle?.text = eventEntity.name
        val toolbar =
            requireParentFragment().view?.findViewById<Toolbar>(R.id.eventDetailsToolBar)
        if (viewModel.isFavriote.value == true) {
            toolbar?.menu?.findItem(R.id.favorites)?.setIcon(R.drawable.heart_filled)
        } else {
            toolbar?.menu?.findItem(R.id.favorites)?.setIcon(R.drawable.heart_outline)
        }


        if (url != null) {
            toolbar?.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.faceBook -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data =
                            Uri.parse("https://www.facebook.com/sharer/sharer.php?u=${url}")
                        startActivity(intent)
                        true
                    }

                    R.id.twitter -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data =
                            Uri.parse("http://twitter.com/share?text=Check ${eventEntity.name} Tour on Ticketmaster.&url=${url}")
                        intent.setPackage("com.android.chrome")
                        startActivity(intent)
                        true
                    }

                    R.id.favorites -> {
                        if (viewModel.isFavriote.value === true) {
                            it.setIcon(R.drawable.heart_outline)
                            var snackbar =
                                Snackbar.make(
                                    binding.eventDetailsCardView,
                                    eventEntity.name + " removed from favriotes",
                                    Snackbar.LENGTH_LONG
                                )
                            snackbar.show()
                            viewModel.isFavriote.postValue(false)
                            viewModel.removeEvent(eventEntity)
                        } else {
                            it.setIcon(R.drawable.heart_filled)
                            var snackbar =
                                Snackbar.make(
                                    binding.eventDetailsCardView,
                                    eventEntity.name + " added to favriotes",
                                    Snackbar.LENGTH_LONG
                                )
                            snackbar.show()
                            viewModel.isFavriote.postValue(true)
                            viewModel.insert(eventEntity)
                        }
                        true
                    }

                    else -> {
                        false
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun bindTicketStatus(code: String) {
        val drawable =
            ContextCompat.getDrawable(requireContext(), R.drawable.ticket_status_background)
        var color = android.R.color.transparent
        when (code) {
            "onsale" -> {
                binding.ticketStatusTxt.text = "On Sale"
                color = resources.getColor(R.color.onsale)
            }

            "offsale" -> {
                binding.ticketStatusTxt.text = "Off Sale"
                color = resources.getColor(R.color.offsale)
            }

            "cancelled" -> {
                binding.ticketStatusTxt.text = "Cancelled"
                color = resources.getColor(R.color.cancelled)
            }

            "postponed" -> {
                binding.ticketStatusTxt.text = "Postponed"
                color = resources.getColor(R.color.postponed_rescheduled)
            }

            "rescheduled" -> {
                binding.ticketStatusTxt.text = "Rescheduled"
                color = resources.getColor(R.color.postponed_rescheduled)
            }
        }
        drawable?.mutate()?.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        binding.ticketStatusTxt.background = drawable
    }

    private fun bindBuyTicketURL(url: String?) {
        if (url != null) {
            binding.buyTicketAtText.text = url
            binding.buyTicketAtText.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        } else {
            binding.buyTicketAtText.visibility = View.GONE
            binding.buyTicket.visibility = View.GONE
        }
    }

}