package com.example.pants.utils.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.pants.R

class ErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val message = arguments?.getString(ARG_MESSAGE).orEmpty()

        return AlertDialog.Builder(requireContext(), R.style.ErrorAlertDialog)
            .setTitle(getString(R.string.error))
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    companion object {
        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): ErrorDialogFragment {
            val args = Bundle().apply {
                putString(ARG_MESSAGE, message)
            }
            return ErrorDialogFragment().apply {
                arguments = args
            }
        }
    }
}
