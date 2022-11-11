package com.panasetskaia.feature_details.presenation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.panasetskaia.feature_details.databinding.FragmentTabDefaultBinding

class TabDefaultFragment : Fragment() {

    private var _binding: FragmentTabDefaultBinding? = null
    private val binding: FragmentTabDefaultBinding
        get() = _binding ?: throw RuntimeException("FragmentTabDefaultBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabDefaultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}