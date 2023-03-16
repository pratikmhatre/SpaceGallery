package com.cypher.spacegallery.gallery_details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cypher.spacegallery.core.helpers.collectWhileStarted
import com.cypher.spacegallery.databinding.FragmentGalleryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GalleryDetailsFragment : Fragment() {
    private var _binding: FragmentGalleryDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GalleryDetailsViewModel by viewModels()

    @Inject
    lateinit var galleryDetailsAdapter: GalleryDetailsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = galleryDetailsAdapter
        galleryDetailsAdapter.setOnBackPressListener {
            findNavController().navigateUp()
        }
        viewModel.galleryDetailsListFlow.collectWhileStarted(viewLifecycleOwner) {
            binding.progressBar.isVisible = it.isLoading
            it.galleryDetailsList?.run {
                galleryDetailsAdapter.galleryList = this
                binding.viewPager.setCurrentItem(arguments?.getInt("position") ?: 1, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}