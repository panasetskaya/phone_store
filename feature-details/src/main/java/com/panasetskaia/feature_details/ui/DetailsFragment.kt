package com.panasetskaia.feature_details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.widget.ViewPager2
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.feature_details.R
import com.panasetskaia.feature_details.adapters.HorizontalMarginItemDecoration
import com.panasetskaia.feature_details.adapters.PhoneImagesListAdapter
import com.panasetskaia.feature_details.databinding.FragmentDetailsBinding
import com.panasetskaia.feature_details.viewmodels.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Math.abs

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var phoneImagesListAdapter: PhoneImagesListAdapter

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailsBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        collectFlows()
        setupViewPagers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewPagers() {
        phoneImagesListAdapter = PhoneImagesListAdapter()
        binding.viewPagerPhonePics.adapter = phoneImagesListAdapter
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            // Next line scales the item's height. You can remove it if you don't want this effect
            page.scaleY = 1 - (0.25f * abs(position))
            // If you want a fading effect uncomment the next line:
            // page.alpha = 0.25f + (1 - abs(position))
        }
        binding.viewPagerPhonePics.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            this@DetailsFragment.requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.viewPagerPhonePics.addItemDecoration(itemDecoration)

    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.phoneStateFlow.collectLatest {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            binding.progressBar.visibility = View.GONE
                            phoneImagesListAdapter.submitList(it.data?.images)
                        }
                        else -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@DetailsFragment.requireContext(),
                                "Cannot load: ${it.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            }
        }
    }


}