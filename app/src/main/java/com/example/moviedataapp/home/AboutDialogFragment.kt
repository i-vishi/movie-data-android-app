package com.example.moviedataapp.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.moviedataapp.R

class AboutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(inflater.inflate(R.layout.dialog_about, null))
                // Add action buttons
                .setNeutralButton(
                    R.string.close_dialog
                ) { _, _ -> dialog?.cancel() }
            builder.create()

        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        const val TAG = "AboutDialog"
    }
}
