package com.example.pants.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.pants.databinding.FragmentPickerBinding
import com.example.pants.presentation.viewmodel.SharedGameViewModel
import com.example.pants.presentation.ui.GameFragment.Companion.COLOR_NAME_ARG
import com.example.pants.ui.theme.PantsAppTheme
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class ColorPickerFragment : Fragment() {
    private var _viewBinding: FragmentPickerBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel by activityViewModel<SharedGameViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _viewBinding = FragmentPickerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colorName = arguments?.getString(COLOR_NAME_ARG)
        colorName?.let { name ->
            viewModel.setColorModelByName(name)
        }
        bindCompose()
    }

    private fun bindCompose() {
        viewBinding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                PantsAppTheme {
                    Box(modifier = Modifier.fillMaxSize()) {
                        ColorPickerScreen(viewModel) {
                            parentFragmentManager.popBackStack()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
