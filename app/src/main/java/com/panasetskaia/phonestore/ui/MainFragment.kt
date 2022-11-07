package com.panasetskaia.phonestore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.panasetskaia.phonestore.R

class MainFragment : Fragment() {

    lateinit var closeDialog: ImageView
    lateinit var applyFilters: Button
    lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetDialog = BottomSheetDialog(requireContext())
        setListeners()
    }

    private fun setListeners() {
        val filterButton: ImageView? = getView()?.findViewById(R.id.filter_button)
        filterButton?.setOnClickListener {
            showBottomSheetDialog()
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
}