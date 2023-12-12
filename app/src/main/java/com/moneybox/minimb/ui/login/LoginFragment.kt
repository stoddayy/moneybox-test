package com.moneybox.minimb.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moneybox.minimb.extensions.createRootComposeView

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val composeView = createRootComposeView()

        composeView.setContent {
            LoginScreen(

            )
        }

        return composeView
    }

}