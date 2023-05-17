package com.example.amernotsapp.ui.screen.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.amernotsapp.AmernotsAppAplication
import com.example.amernotsapp.R
import com.example.amernotsapp.data.model.request.SignInRequest
import com.example.amernotsapp.databinding.FragmentSignInBinding
import com.example.amernotsapp.di.appComponent
import com.example.amernotsapp.di.lazyViewModel
import com.example.amernotsapp.ui.vm.SIgnInFragmentViewModel
import retrofit2.HttpException

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private var binding: FragmentSignInBinding? = null

    private val viewModel: SIgnInFragmentViewModel by lazyViewModel {
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
                if (userLogin.matches(Regex("^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"))) {
                    viewModel.requestTokenAuthBySignIn(
                        SignInRequest(
                            login = userLogin,
                            password = userPassword
                        )
                    )
                }  else {
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
                TokenAuthDataModel?.let {data ->
                    Toast.makeText(
                        requireContext(),
                        "TokenAuth: " + data.token,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            viewModel.errorState.observe(viewLifecycleOwner) { ex ->
                ex?.let {
                    val errorMessage = (ex as? HttpException)?.message() ?: ex.toString()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.exception_occurred_pattern,errorMessage),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}