package com.moneybox.minimb.ui.login.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.moneybox.minimb.extensions.collectSafely
import com.moneybox.minimb.extensions.createRootComposeView
import kotlinx.coroutines.flow.receiveAsFlow

class LoginFragment : Fragment() {

    private val viewModel by viewModels<LoginViewModel> {
        loginViewModelFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val composeView = createRootComposeView()

        composeView.setContent {
            LoginScreen(
                viewModel = viewModel
            )
        }

        return composeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.navigation.receiveAsFlow().collectSafely(viewLifecycleOwner) { event ->
            when (event) {
                LoginNavEvent.NextScreen -> findNavController().navigate(LoginFragmentDirections.toPlanValue())
            }
        }
    }
}