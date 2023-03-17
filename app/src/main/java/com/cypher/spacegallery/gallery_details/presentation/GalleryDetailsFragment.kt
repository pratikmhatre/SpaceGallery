package com.cypher.spacegallery.gallery_details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.cypher.spacegallery.R
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
    private var pagerPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            val safeArgs = GalleryDetailsFragmentArgs.fromBundle(this)
            pagerPosition = safeArgs.position
        }
    }

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
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                pagerPosition = position
            }
        })

        galleryDetailsAdapter.setOnBackPressListener {
            findNavController().navigate(R.id.GalleryListFragment)
        }
        initObservers()
    }

    private fun initObservers() {
        viewModel.galleryDetailsListFlow.collectWhileStarted(viewLifecycleOwner) {
            binding.progressBar.isVisible = it.isLoading
            it.galleryDetailsList?.run {
                galleryDetailsAdapter.galleryList = this
                binding.viewPager.setCurrentItem(pagerPosition, false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}