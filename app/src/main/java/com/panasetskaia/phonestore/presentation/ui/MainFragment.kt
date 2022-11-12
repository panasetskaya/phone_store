package com.panasetskaia.phonestore.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.core.navigation.NavCommand
import com.panasetskaia.core.utils.ViewModelFactory
import com.panasetskaia.core.utils.navigate
import com.panasetskaia.feature_cart.di.CartComponentProvider
import com.panasetskaia.phonestore.R
import com.panasetskaia.phonestore.application.StoreApplication
import com.panasetskaia.phonestore.databinding.FragmentMainBinding
import com.panasetskaia.phonestore.presentation.adapters.BestSellersListAdapter
import com.panasetskaia.phonestore.presentation.adapters.HotSalesListAdapter
import com.panasetskaia.phonestore.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    private lateinit var closeDialog: ImageView
    private lateinit var applyFilters: Button
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bestSellersListAdapter: BestSellersListAdapter
    private lateinit var hotSalesListAdapter: HotSalesListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as StoreApplication).component.injectMainFragment(this)
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        setupRecyclers()
        setListeners()
        setupBottomBar()
        collectFlows()
    }

    private fun setupRecyclers() {
        bestSellersListAdapter = BestSellersListAdapter()
        bestSellersListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        with(binding.recyclerViewBestSellers) {
            adapter = bestSellersListAdapter
            bestSellersListAdapter.onItemClickListener = {
                navigate(NavCommand(R.id.action_mainFragment_to_detailsFragment), R.id.fcvMain)
            }
        }
        hotSalesListAdapter = HotSalesListAdapter()
        hotSalesListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        with(binding.recyclerViewHotSales) {
            adapter = hotSalesListAdapter
            hotSalesListAdapter.onItemClickListener = {
                navigate(NavCommand(R.id.action_mainFragment_to_detailsFragment), R.id.fcvMain)
            }
        }
    }

    private fun setListeners() {
        binding.filterButton.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.seeAllBestSellersTv.setOnClickListener {
            Toast.makeText(
                this@MainFragment.requireContext(),
                "See more Best Sellers",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.seeAllTv.setOnClickListener {
            Toast.makeText(
                this@MainFragment.requireContext(),
                "View all categories",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.seeAllHotSalesTv.setOnClickListener {
            Toast.makeText(
                this@MainFragment.requireContext(),
                "See more Hot Sales",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupBottomBar() {
        val badge = binding.mainBottomToolbar.root.getOrCreateBadge(com.panasetskaia.core.R.id.to_cart)
        badge.backgroundColor = ContextCompat.getColor(this.requireContext(), com.panasetskaia.core.R.color.peach_color)
        binding.mainBottomToolbar.root.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                com.panasetskaia.core.R.id.to_cart -> {
                    navigate(NavCommand(R.id.action_mainFragment_to_cartFragment), R.id.fcvMain)
                    true
                }
                com.panasetskaia.core.R.id.to_account -> {
                    Toast.makeText(
                        this@MainFragment.requireContext(),
                        "Go to your account",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                } else -> {
                Toast.makeText(
                    this@MainFragment.requireContext(),
                    "Go to your favourites",
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
            }
        }
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.hotSalesStateFlow.collectLatest {
                        when (it.status) {
                            Status.LOADING -> {
                                binding.progressBarRVHotSales.visibility = View.VISIBLE
                            }
                            Status.SUCCESS -> {
                                binding.progressBarRVHotSales.visibility = View.INVISIBLE
                                hotSalesListAdapter.submitList(it.data)
                            }
                            Status.ERROR -> {
                                binding.progressBarRVHotSales.visibility = View.INVISIBLE
                                Toast.makeText(
                                    this@MainFragment.requireContext(),
                                    "Cannot load: ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                launch {
                    viewModel.bestSellersStateFlow.collectLatest {
                        when (it.status) {
                            Status.LOADING -> {
                                binding.progressBarRVBestSellers.visibility = View.VISIBLE
                            }
                            Status.SUCCESS -> {
                                binding.progressBarRVBestSellers.visibility = View.GONE
                                bestSellersListAdapter.submitList(it.data)
                            }
                            Status.ERROR -> {
                                binding.progressBarRVBestSellers.visibility = View.GONE
                            }
                        }
                    }
                }
                launch {
                    viewModel.cartSizeFlow.collectLatest {
                        val badge = binding.mainBottomToolbar.root.getBadge(com.panasetskaia.core.R.id.to_cart)
                        if (it>0) {
                            badge?.isVisible = true
                            badge?.number = it
                        } else {
                            badge?.isVisible = false
                        }
                    }
                }
            }
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_dialog_layout, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        setSpinner(bottomSheetView.findViewById(R.id.spinner_brand), R.array.brands_array)
        setSpinner(bottomSheetView.findViewById(R.id.spinner_price), R.array.price_array)
        setSpinner(bottomSheetView.findViewById(R.id.spinner_size), R.array.size_array)
        closeDialog = bottomSheetView.findViewById(R.id.close_dialog_button)
        closeDialog.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        applyFilters = bottomSheetView.findViewById(R.id.apply_filters_button)
        applyFilters.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun setSpinner(spinner: Spinner, array: Int) {
        ArrayAdapter.createFromResource(
            this@MainFragment.requireContext(),
            array,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item)
            spinner.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}