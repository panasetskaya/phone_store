package com.panasetskaia.phonestore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.panasetskaia.phonestore.R

class MainFragment : Fragment() {

    lateinit var closeDialog: ImageView
    lateinit var applyFilters: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        val filterButton: ImageView? = getView()?.findViewById(R.id.filter_button)
        filterButton?.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout)
        closeDialog = bottomSheetDialog.findViewById(R.id.close_dialog_button)!!
        closeDialog.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        applyFilters = bottomSheetDialog.findViewById(R.id.apply_filters_button)!!
        applyFilters.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()


    }
}