package com.example.pants.utils.extension

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.pants.utils.extension.DialogTags.ERROR_DIALOG_TAG
import com.example.pants.utils.dialog.ErrorDialogFragment

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showErrorDialog(message: String) {
    val dialog = ErrorDialogFragment.newInstance(message)
    dialog.show(childFragmentManager, ERROR_DIALOG_TAG)
}

object DialogTags {
    const val ERROR_DIALOG_TAG = "ErrorDialog"
}
