package com.panasetskaia.phonestore.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.panasetskaia.phonestore.R
import com.panasetskaia.phonestore.databinding.FragmentMainBinding
import com.panasetskaia.phonestore.presentation.adapters.BestSellersListAdapter
import com.panasetskaia.phonestore.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private lateinit var closeDialog: ImageView
    private lateinit var applyFilters: Button
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var viewModel: MainViewModel
    private lateinit var listAdapter: BestSellersListAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclers()
        setListeners()
        collectFlows()
    }

    private fun setupRecyclers() {
        listAdapter = BestSellersListAdapter()
        listAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        with(binding.recyclerViewBestSellers) {
            adapter = listAdapter
            listAdapter.onItemClickListener = {
                Toast.makeText(
                    this@MainFragment.requireContext(),
                    "Will go to Details",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setListeners() {
        binding.filterButton.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getHotSales().collectLatest {
//                        Toast.makeText(this@MainFragment.requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                    }
                }
                launch {
                    viewModel.getBestSellers().collectLatest {
                        listAdapter.submitList(it)
                    }
                }
            }
        }
    }

    //todo: переделать на биндинг!
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