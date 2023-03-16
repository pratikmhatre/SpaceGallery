package com.cypher.spacegallery.gallery_list.presentation

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cypher.spacegallery.core.helpers.Utils
import com.cypher.spacegallery.core.helpers.collectWhileStarted
import com.cypher.spacegallery.databinding.FragmentGalleryListBinding
import com.cypher.spacegallery.gallery_list.presentation.adapters.GalleryListAdapter
import com.cypher.spacegallery.gallery_list.presentation.events.UiEvents
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class GalleryListFragment : Fragment() {
    private var _binding: FragmentGalleryListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GalleryListViewModel by viewModels()

    @Inject
    lateinit var galleryListAdapter: GalleryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setActionBar(binding.toolbar)
        galleryListAdapter.setOnItemClickListener { index, _ ->
            viewModel.onGalleryItemClicked(index)
        }
        binding.rvGalleryList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = galleryListAdapter
            if (itemDecorationCount == 0) {
                addItemDecoration(getItemDecoration())
            }
        }
        viewModel.galleryDataFlow.collectWhileStarted(viewLifecycleOwner) {
            binding.progressBar.isVisible = it.isLoading
            it.galleryData?.let { list ->
                galleryListAdapter.galleryList = list
            }
        }
        viewModel.uiEventsFlow.collectWhileStarted(viewLifecycleOwner) {
            when (it) {
                is UiEvents.ShowErrorMessage -> showErrorMessage(it.message)
                is UiEvents.OpenDetailsPage -> openDetailsFragment(it.position)
            }
        }
    }

    private fun getItemDecoration(): RecyclerView.ItemDecoration {
        return object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val childAdapterPosition = parent.getChildAdapterPosition(view)

                if (childAdapterPosition == 0 || childAdapterPosition == 1) {
                    outRect.top = Utils.dpToPx(requireContext(), 8)
                }
                if (childAdapterPosition % 2 == 0) {
                    outRect.apply {
                        left = Utils.dpToPx(requireContext(), 8)
                        right = Utils.dpToPx(requireContext(), 4)
                    }
                } else {
                    outRect.apply {
                        right = Utils.dpToPx(requireContext(), 8)
                        left = Utils.dpToPx(requireContext(), 4)
                    }
                }
                outRect.bottom = Utils.dpToPx(requireContext(), 8)

            }
        }
    }

    private fun openDetailsFragment(position: Int) {

    }

    private fun showErrorMessage(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}