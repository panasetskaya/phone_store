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
import android.widget.Toast
import androidx.core.view.marginLeft
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectFlows() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.phoneStateFlow.collectLatest {
                    when (it.status) {
                        Status.SUCCESS -> {

                            with(binding) {
                                cameraDetails.text = it.data?.camera
                                cpuName.text = it.data?.CPU
                                sdDetails.text = it.data?.sd
                                ssdDetails.text = it.data?.ssd
                                setRadioGroups(
                                    it.data?.capacities ?: listOf(),
                                    it.data?.colors ?: listOf()
                                )
                            }
                        }
                        Status.ERROR -> {
                            viewModel.setTestingPhone()
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
        binding.radioGroupColors.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.changeColor(colors[i])
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
            radioButton.text = i + " GB"
            radioButton.gravity = Gravity.CENTER
            radioButton.setTextColor(resources.getColorStateList(R.color.capacities_radio_colors))
            radioButton.background = resources.getDrawable(R.drawable.capacities_buttons)
            binding.radioGroupCapacities.addView(radioButton)
        }
        binding.radioGroupCapacities.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.changeCapacity(capacities[i])
        }
    }
}
