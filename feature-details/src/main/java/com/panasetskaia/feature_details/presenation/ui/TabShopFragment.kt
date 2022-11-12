package com.panasetskaia.feature_details.presenation.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.panasetskaia.core.domain.entities.Phone
import com.panasetskaia.core.domain.entities.Status
import com.panasetskaia.feature_details.R
import com.panasetskaia.feature_details.databinding.FragmentTabShopBinding
import com.panasetskaia.feature_details.presenation.viewmodels.DetailsViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TabShopFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel

    private var currentPhone = Phone(0)
    private var colors = listOf<String>()
    private var capacities = listOf<String>()

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
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        binding.addToCartButton.setOnClickListener {
            val chColor = colors[binding.radioGroupColors.checkedRadioButtonId]
            val chCapacity = capacities[binding.radioGroupCapacities.checkedRadioButtonId]
            val newPhone = currentPhone.copy(
                id = 0,
                quantity = 1,
                chosenColor = chColor,
                chosenCapacity = chCapacity
            )
            viewModel.addToCart(newPhone)
        }
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.phoneStateFlow.collectLatest {
                    when (it.status) {
                        Status.SUCCESS -> {
                            it.data?.let { phone ->
                                currentPhone = phone
                                with(binding) {
                                    cameraDetails.text = phone.camera
                                    cpuName.text = phone.CPU
                                    sdDetails.text = phone.sd
                                    ssdDetails.text = phone.ssd
                                    priceForCart.text = resources.getString(com.panasetskaia.core.R.string.price_for_cart, phone.price)
                                    if (phone.capacities!=null) {
                                        capacities = phone.capacities as List<String>
                                    }
                                    if (phone.colors!=null) {
                                        colors = phone.colors as List<String>
                                    }
                                    setRadioGroups(
                                        phone.capacities ?: listOf(),
                                        phone.colors ?: listOf()
                                    )
                                }
                            }
                        }
                        Status.ERROR -> {
//                            viewModel.setTestingPhone()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setRadioGroups(
        capacities: List<String>,
        colors: List<String>
    ) {
        for (i in colors) {
            val radioButton = RadioButton(this@TabShopFragment.requireContext())
            radioButton.id = colors.indexOf(i)
            if (colors.indexOf(i)==0) {
                radioButton.isChecked = true
            }
            val params =
                RadioGroup.LayoutParams(150, 150)
            params.leftMargin = 32
            params.rightMargin = 32
            params.gravity = Gravity.CENTER
            radioButton.layoutParams = params
            radioButton.scaleX = 1.7f
            radioButton.scaleY = 1.7f
            radioButton.setButtonDrawable(R.drawable.color_radio_icon_selector)
            radioButton.buttonTintList = ColorStateList.valueOf(Color.parseColor(i))
            binding.radioGroupColors.addView(radioButton)
        }

        for (i in capacities) {
            val radioButton = RadioButton(this@TabShopFragment.requireContext())
            radioButton.id = capacities.indexOf(i)
            if (capacities.indexOf(i)==0) {
                radioButton.isChecked = true
            }
            val params =
                RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, 120)
            params.leftMargin = 32
            params.weight = 1f
            radioButton.layoutParams = params
            radioButton.buttonDrawable = null
            radioButton.text = resources.getString(com.panasetskaia.core.R.string.gb, i)
            radioButton.gravity = Gravity.CENTER
            radioButton.setTextColor(ContextCompat.getColorStateList(this.requireContext(),R.color.capacities_radio_colors))
            radioButton.background = ResourcesCompat.getDrawable(resources,R.drawable.capacities_buttons,null)
            binding.radioGroupCapacities.addView(radioButton)
        }
    }
}
