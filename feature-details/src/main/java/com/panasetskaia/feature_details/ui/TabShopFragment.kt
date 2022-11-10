package com.panasetskaia.feature_details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.feature_details.adapters.CapacitiesListAdapter
import com.panasetskaia.feature_details.adapters.ColorsListAdapter
import com.panasetskaia.feature_details.databinding.FragmentTabShopBinding
import com.panasetskaia.feature_details.viewmodels.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TabShopFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var colorsListAdapter: ColorsListAdapter
    private lateinit var capacitiesListAdapter: CapacitiesListAdapter

    private var _binding: FragmentTabShopBinding? = null
    private val binding: FragmentTabShopBinding
        get() = _binding ?: throw RuntimeException("FragmentTabShopBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        collectFlows()
        setAdapters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setAdapters() {
        colorsListAdapter = ColorsListAdapter(viewModel, this)
        colorsListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        with(binding.recyclerViewColors) {
            adapter = colorsListAdapter
            colorsListAdapter.onItemClickListener = {
                viewModel.changeColor(it)
            }
        }
        capacitiesListAdapter = CapacitiesListAdapter(viewModel,this)
        capacitiesListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        with(binding.recyclerViewCapacities) {
            adapter = capacitiesListAdapter
            capacitiesListAdapter.onItemClickListener = {
                viewModel.changeCapacity(it)
            }
        }
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.phoneStateFlow.collectLatest {
                    when (it.status) {
                        Status.SUCCESS -> {
                            colorsListAdapter.submitList(it.data?.colors)
                            capacitiesListAdapter.submitList(it.data?.capacities)
                            with (binding) {
                                cameraDetails.text = it.data?.camera
                                cpuName.text = it.data?.CPU
                                sdDetails.text = it.data?.sd
                                ssdDetails.text = it.data?.ssd

                            }
                        }
                        Status.ERROR -> {
                            testing()
                            }
                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun testing() {
        colorsListAdapter.submitList(listOf("#eb4034", "#e8b8b5", "#d6cd22"))
        capacitiesListAdapter.submitList(listOf("256", "128", "133", "000"))
        with(binding) {
            cameraDetails.text = "mock camera"
            cpuName.text = "mock cpu"
            sdDetails.text = "mock sd"
            ssdDetails.text = "mock ssd"
        }
    }
}
