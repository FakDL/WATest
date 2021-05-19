package com.example.watest.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.domain.models.Coordinates
import com.example.domain.models.Landmark
import com.example.watest.R
import com.example.watest.databinding.FragmentDetailsBinding
import com.example.watest.viewmodels.DetailsFetchingViewState
import com.example.watest.viewmodels.DetailsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import kotlinx.coroutines.launch


class DetailsFragment(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment(), OnMapReadyCallback {

    private var landId: Int = 0

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetailsFragmentArgs by navArgs()
        landId = args.landId
        fetchData(landId)
        initMap(savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.fabLike.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.isFavoriteLand(landId)) {
                    setFabIcon(false)
                    viewModel.dislikeLand(landId)
                }else{
                    setFabIcon(true)
                    viewModel.likeLand(landId)
                }
            }
        }
    }

    private fun fetchData(id: Int) {
        lifecycleScope.launch {
            viewModel.getLandById(id)
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is DetailsFetchingViewState.Success -> {
                    binding.pbLoading.isVisible = false
                    binding.layoutDetails.isVisible = true
                    setLandmarkDetails(state.landmark)
                }
                is DetailsFetchingViewState.FetchingFailed -> {
                    binding.pbLoading.isVisible = false
                }
                is DetailsFetchingViewState.Loading -> {
                    binding.pbLoading.isVisible = true
                    binding.layoutDetails.isVisible = false
                }
            }
        })

    }

    private fun initMap(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)
    }

    private fun setLandmarkDetails(landmark: Landmark) {
        setLandIcon(landmark.imageName)
        setTitle(landmark.name)
        lifecycleScope.launch { setFabIcon(viewModel.isFavoriteLand(landId)) }
        setMap(landmark.coordinates)
        binding.tvCity.text =  getString(R.string.city, landmark.city)
        binding.tvDesc.text =  getString(R.string.description, landmark.park)
        binding.tvRegion.text =  getString(R.string.region, landmark.state)
        binding.tvCategory.text = getString(R.string.category, landmark.category)

    }

    private fun setMap(coordinates: Coordinates) {
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isZoomGesturesEnabled = false
        val location = LatLng(coordinates.latitude, coordinates.longitude)
        googleMap.addMarker(
            MarkerOptions().position(location)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        googleMap.setMinZoomPreference(5.0F)
    }

    private fun setupMapClickListener(map: GoogleMap?) {
        map?.setOnMapClickListener {
            val latitude = it.latitude
            val longitude = it.longitude
            val label = "ABC Label"
            val uriBegin = "geo:$latitude,$longitude"
            val query = "$latitude,$longitude($label)"
            val encodedQuery: String = Uri.encode(query)
            val uriString = "$uriBegin?q=$encodedQuery&z=16"
            val uri: Uri = Uri.parse(uriString)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun setTitle(title: String) {
        var isShow = true
        var scrollRange = -1
        binding.abLayout.addOnOffsetChangedListener(OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                binding.collapsingToolbar.title = title
                isShow = true
            } else if (isShow) {
                binding.collapsingToolbar.title = " "
                isShow = false
            }
        })
    }

    private fun setFabIcon(isFavorite: Boolean) {
        if (isFavorite) binding.fabLike.setImageResource(R.drawable.ic_liked)
        else binding.fabLike.setImageResource(R.drawable.ic_disliked)
    }

    private fun setLandIcon(imageName: String) {
        var resId = context?.resources?.getIdentifier(imageName, "drawable", context?.packageName)
        resId = if (resId == 0 || resId == null) R.drawable.placeholder else resId
        binding.ivPoster.setImageResource(resId)
    }

    override fun onMapReady(map: GoogleMap?) {
        setupObservers()
        map?.let {
            googleMap = it
        }
        setupMapClickListener(map)

    }

}
