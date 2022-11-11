package com.panasetskaia.feature_cart.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.feature_cart.presentation.adapters.PhoneListAdapter
import com.panasetskaia.feature_cart.databinding.FragmentCartBinding
import com.panasetskaia.feature_cart.presentation.viewmodels.CartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment() {

    lateinit var phoneListAdapter: PhoneListAdapter
    lateinit var viewModel: CartViewModel

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding
        get() = _binding ?: throw RuntimeException("FragmentCartBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        setAdapter()
        collectFlows()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        binding.goBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setAdapter() {
        phoneListAdapter = PhoneListAdapter(viewModel)
        phoneListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        with(binding.recyclerViewCartItems) {
            adapter = phoneListAdapter
        }
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartItemsFlow.collectLatest {
                    phoneListAdapter.submitList(it)
                    binding.tvSum.text = "$" + getPriceSum(it).toString() + ".00"
                }
            }
        }
    }

    private fun getPriceSum(list: List<Phone>): Int {
        var sum = 0
        for (i in list) {
            sum+= i.price?.times(i.quantity) ?: 0
        }
        return sum
    }

}