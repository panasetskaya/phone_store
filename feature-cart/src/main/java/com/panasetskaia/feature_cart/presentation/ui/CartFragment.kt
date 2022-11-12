package com.panasetskaia.feature_cart.presentation.ui

import android.content.Context
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
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.utils.ViewModelFactory
import com.panasetskaia.core.utils.goBack
import com.panasetskaia.feature_cart.databinding.FragmentCartBinding
import com.panasetskaia.feature_cart.di.CartComponentProvider
import com.panasetskaia.feature_cart.navigation.CartNavCommandProvider
import com.panasetskaia.feature_cart.presentation.adapters.PhoneListAdapter
import com.panasetskaia.feature_cart.presentation.viewmodels.CartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var cartNavCommandProvider: CartNavCommandProvider

    val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]
    }

    lateinit var phoneListAdapter: PhoneListAdapter

    private var _binding: FragmentCartBinding? = null
    private val binding: FragmentCartBinding
        get() = _binding ?: throw RuntimeException("FragmentCartBinding is null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as CartComponentProvider)
            .getCartComponent()
            .injectCartFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            goBack(cartNavCommandProvider.navHost)
        }
    }

    private fun setAdapter() {
        phoneListAdapter = PhoneListAdapter(viewModel, this)
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
                    binding.tvSum.text = resources.getString(com.panasetskaia.core.R.string.price_for_cart,getPriceSum(it))
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