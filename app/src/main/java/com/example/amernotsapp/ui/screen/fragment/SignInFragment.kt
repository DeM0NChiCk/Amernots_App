package com.example.amernotsapp.ui.screen.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.amernotsapp.AmernotsAppAplication
import com.example.amernotsapp.R
import com.example.amernotsapp.data.api.model.request.SignInRequest
import com.example.amernotsapp.databinding.FragmentSignInBinding
import com.example.amernotsapp.di.appComponent
import com.example.amernotsapp.di.lazyViewModel
import com.example.amernotsapp.ui.enums.TokenError
import com.example.amernotsapp.ui.preferences.CredentialsPreferences
import com.example.amernotsapp.ui.screen.activity.MainActivity
import com.example.amernotsapp.ui.vm.SignInFragmentViewModel
import retrofit2.HttpException

class SignInFragment() : Fragment(R.layout.fragment_sign_in) {

    private var binding: FragmentSignInBinding? = null


    private val viewModel: SignInFragmentViewModel by lazyViewModel {
        requireContext().appComponent().signInViewModel().create(assistedValue = "AssistedValue")
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as? AmernotsAppAplication)?.appComponent?.injectSignIn(fragment = this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInBinding.bind(view)

        initViews()
        observerData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initViews() {
        binding?.apply {
            btnRegNewUser.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_regFragment)
            }

            btnSingIn.setOnClickListener {
                val userLogin = editAuthLogin.text.toString()
                val userPassword = editAuthPassword.text.toString()

                if (userLogin.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Fields cannot be empty!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@setOnClickListener
                }
                if (userLogin.matches(Regex(VALIDATE_LOGIN))) {
                    viewModel.requestTokenAuthBySignIn(
                        SignInRequest(
                            login = userLogin,
                            password = userPassword
                        )
                    )
                } else {
                    Toast.makeText(
                        context,
                        "Invalid login",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@setOnClickListener
                }
            }
        }
    }

    private fun observerData() {
        binding?.apply {
            viewModel.signInDataState.observe(viewLifecycleOwner) { TokenAuthDataModel ->
                TokenAuthDataModel?.let { data ->
                    if (data.token != "null") {
                        onAuthSuccess(System.currentTimeMillis() / 1000, data.token)
                    } else {
                        onAuthFailed(TokenError.TOKEN_NOT_VALIDATE)
                    }
                }
            }


            viewModel.errorState.observe(viewLifecycleOwner) { ex ->
                ex?.let {
                    val errorMessage = (ex as? HttpException)?.message() ?: ex.toString()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.exception_occurred_pattern, errorMessage),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun onAuthSuccess(timestamp: Long, tokenAuth: String) {
        with(CredentialsPreferences.getCredentialsPreferences(requireContext()).edit()) {
            putLong(CredentialsPreferences.TIMESTAMP_TOKEN_AUTH, timestamp)
            putString(CredentialsPreferences.TOKEN_AUTH, tokenAuth)
            apply()
        }

        findNavController().setGraph(R.navigation.bottom_graph)

        (requireContext() as MainActivity).changeBtnNavVisibility(true)
    }

    private fun onAuthFailed(error: TokenError) {
        when (error) {
            TokenError.TOKEN_NOT_VALIDATE -> {
                displayErrorToast(R.string.token_not_validate)
            }
            TokenError.TOKEN_NOT_FOUND -> {
                displayErrorToast(R.string.unknown_error)
            }
        }
    }

    private fun displayErrorToast(@StringRes error: Int) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        private const val VALIDATE_LOGIN = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"
    }
}