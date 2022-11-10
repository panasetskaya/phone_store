package com.panasetskaia.feature_details.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.panasetskaia.feature_details.R
import com.panasetskaia.feature_details.adapters.PhoneImagesListAdapter
import com.panasetskaia.feature_details.databinding.FragmentDetailsBinding
import com.panasetskaia.feature_details.databinding.FragmentTabShopBinding
import com.panasetskaia.feature_details.viewmodels.DetailsViewModel

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}