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
import com.google.android.material.tabs.TabLayoutMediator
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.feature_details.R
import com.panasetskaia.feature_details.adapters.HorizontalMarginItemDecoration
import com.panasetskaia.feature_details.adapters.ParentCategoryPagerAdapter
import com.panasetskaia.feature_details.adapters.PhoneImagesListAdapter
import com.panasetskaia.feature_details.databinding.FragmentDetailsBinding
import com.panasetskaia.feature_details.viewmodels.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Math.abs

class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var phoneImagesListAdapter: PhoneImagesListAdapter
    private lateinit var categoryPagerAdapter: ParentCategoryPagerAdapter

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailsBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        collectFlows()
        setupListeners()
        setupImagePager()
        setupFragmentPager()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupImagePager() {
        phoneImagesListAdapter = PhoneImagesListAdapter()
        binding.viewPagerPhonePics.adapter = phoneImagesListAdapter
        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.25f * abs(position))
        }
        binding.viewPagerPhonePics.setPageTransformer(pageTransformer)
        val itemDecoration = HorizontalMarginItemDecoration(
            this@DetailsFragment.requireContext(),
            R.dimen.viewpager_current_item_horizontal_margin
        )
        binding.viewPagerPhonePics.addItemDecoration(itemDecoration)
    }

    private fun setupFragmentPager() {
        categoryPagerAdapter = ParentCategoryPagerAdapter(this)
        binding.viewPagerDetailCategories.adapter = categoryPagerAdapter
        val tabTitles = resources.getStringArray(com.panasetskaia.core.R.array.tabs)
        TabLayoutMediator(binding.tabLayoutDetailCategories, binding.viewPagerDetailCategories) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    private fun setupListeners() {
        binding.goBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.toCartButton.setOnClickListener {
//            replaceWithThisFragment(DetailsFragment::class.java, null)
        }
        binding.isFav.setOnClickListener {
            binding.notFav.visibility = View.VISIBLE
            binding.isFav.visibility = View.GONE
        }
        binding.notFav.setOnClickListener {
            binding.notFav.visibility = View.GONE
            binding.isFav.visibility = View.VISIBLE
        }
    }


    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.phoneStateFlow.collectLatest {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.bottomLayout.visibility = View.INVISIBLE
                        }
                        Status.SUCCESS -> {
                            phoneImagesListAdapter.submitList(it.data?.images)
                            with(binding) {
                                bottomLayout.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                phoneName.text = it.data?.title
                                phoneRatingBar.rating = it.data?.rating ?: 0f
                                priceForCart.text = it.data?.price.toString()
                                if (it.data?.isFavorite==true) {
                                    notFav.visibility = View.GONE
                                    isFav.visibility = View.VISIBLE
                                } else {
                                    notFav.visibility = View.VISIBLE
                                    isFav.visibility = View.GONE
                                }
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
//                            binding.bottomLayout.visibility = View.INVISIBLE
                            testing()
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

    private fun testing() {
        binding.bottomLayout.visibility = View.VISIBLE
        binding.phoneName.text = "Some Phone"
        binding.phoneRatingBar.rating = 3.5f
    }

//    private fun replaceWithThisFragment(fragment: Class<out Fragment>, args: Bundle?) {
//        parentFragmentManager.beginTransaction()
//            .setReorderingAllowed(true)
//            .replace(R.id.fcvMain, fragment, args)
//            .addToBackStack(null)
//            .commit()
//    }
}