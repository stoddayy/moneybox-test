package com.moneybox.minimb.ui.planvalue.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.moneybox.minimb.extensions.createRootComposeView

class PlanValueFragment : Fragment() {

    private val viewModel by viewModels<PlanValueViewModel> {
        planValueViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val composeView = createRootComposeView()

        composeView.setContent {
            PlanValueScreen(
                viewModel = viewModel
            )
        }

        return composeView
    }
}