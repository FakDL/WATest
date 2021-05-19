package com.example.watest.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.domain.models.Landmark
import com.example.watest.R
import com.example.watest.databinding.FragmentListBinding
import com.example.watest.ui.MainActivity
import com.example.watest.ui.recyclerview.LocAdapter
import com.example.watest.viewmodels.ListFetchingViewState
import com.example.watest.viewmodels.ListViewModel
import kotlinx.coroutines.*

class ListFragment(
    private val viewModelFactory: ViewModelProvider.Factory
) : Fragment() {

    private val LANDMARK_POSITION = "landpos"
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private var adapter: LocAdapter? = null

    private val viewModel: ListViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        viewModel.bundle.putParcelable(
            LANDMARK_POSITION, binding.rvLandmarks.layoutManager?.onSaveInstanceState()
        )
        super.onDestroyView()
    }

    private fun setupListeners() {
        binding.tbList.swLiked.setOnCheckedChangeListener { _, isChecked ->
            adapter?.filter(isChecked)
        }
    }

    private fun setupAdapter(list: List<Landmark>) {
        val clickLambda: (Int) -> Unit = { id ->
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(id)
            (activity as MainActivity).navHost.navController.navigate(action)
        }
        val likeLambda: (Int, ImageView) -> Unit = { id, image ->
            lifecycleScope.launch {
                val isFavoriteLand = viewModel.isFavoriteLand(id)
                if (isFavoriteLand) {
                    viewModel.dislikeLand(id)
                    image.setImageResource(R.drawable.ic_disliked)
                } else {
                    viewModel.likeLand(id)
                    image.setImageResource(R.drawable.ic_liked)
                }
            }
        }
        adapter = LocAdapter(clickLambda, likeLambda, list)
        binding.rvLandmarks.layoutManager?.onRestoreInstanceState(
            viewModel.bundle.getParcelable(LANDMARK_POSITION)
        )
    }

    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.fetchData()
        }
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is ListFetchingViewState.Success -> {
                    binding.pbLoading.isVisible = false
                    setupAdapter(state.list)
                    binding.rvLandmarks.adapter = adapter
                }
                is ListFetchingViewState.Loading -> {
                    binding.pbLoading.isVisible = true
                }
            }
        })
    }
}
