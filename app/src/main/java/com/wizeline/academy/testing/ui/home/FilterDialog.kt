package com.wizeline.academy.testing.ui.home

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wizeline.academy.testing.R

class FilterDialog : DialogFragment() {

    private val viewModel: HomeViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = MaterialAlertDialogBuilder(it)

            val optionsList = viewModel.filterOptions
            val selected = optionsList.indexOf(viewModel.filter.value)

            val adapter = ArrayAdapter(
                it,
                android.R.layout.simple_list_item_single_choice,
                optionsList.map { getString(it.label) }
            )
            builder
                .setTitle(R.string.dialog_filter)
                .setSingleChoiceItems(adapter, selected) { dialog, which ->
                    viewModel.selectFilter(optionsList[which])
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}