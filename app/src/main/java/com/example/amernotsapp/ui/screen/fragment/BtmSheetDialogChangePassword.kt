package com.example.amernotsapp.ui.screen.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import com.example.amernotsapp.AmernotsAppAplication
import com.example.amernotsapp.R
import com.example.amernotsapp.data.api.model.request.ChangePasswordRequest
import com.example.amernotsapp.databinding.BtmSheetDialogChangePasswordBinding
import com.example.amernotsapp.ui.enums.ConstValue.Companion.STATUS_SUCCESSFULLY
import com.example.amernotsapp.ui.enums.ConstValue.Companion.STATUS_FAILURE
import com.example.amernotsapp.di.appComponent
import com.example.amernotsapp.di.lazyViewModel
import com.example.amernotsapp.ui.enums.ConstValue
import com.example.amernotsapp.ui.enums.TokenError
import com.example.amernotsapp.ui.preferences.CredentialsPreferences
import com.example.amernotsapp.ui.screen.activity.MainActivity
import com.example.amernotsapp.ui.vm.BtmSheetDialogViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.HttpException

class BtmSheetDialogChangePassword :
    BottomSheetDialogFragment(R.layout.btm_sheet_dialog_change_password) {

    private var binding: BtmSheetDialogChangePasswordBinding? = null

    private val viewModel: BtmSheetDialogViewModel by lazyViewModel {
        requireContext().appComponent().btmShtDialogChangePasswordViewModel()
            .create(assistedValue = "AssistedValue")
    }

    override fun onAttach(context: Context) {
        (requireContext() as? AmernotsAppAplication)?.appComponent?.injectBtmShtDialogChangePassword(
            fragment = this
        )
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BtmSheetDialogChangePasswordBinding.bind(view)

        tryAuth()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onAuthFailed(error: TokenError) {
        when (error) {
            TokenError.TOKEN_NOT_FOUND -> {
                displayErrorToast(R.string.token_not_found)
                findNavController().setGraph(R.navigation.auth_graph)
                (requireContext() as MainActivity).changeBtnNavVisibility(false)
            }

            TokenError.TOKEN_NOT_VALIDATE -> {
                displayErrorToast(R.string.token_not_validate)
                findNavController().setGraph(R.navigation.auth_graph)
                (requireContext() as MainActivity).changeBtnNavVisibility(false)
            }
        }
    }

    private fun tryAuth() {

        val sharedPreferences = CredentialsPreferences.getCredentialsPreferences(requireContext())

        val timestamp = sharedPreferences.getLong(CredentialsPreferences.TIMESTAMP_TOKEN_AUTH, 0L)
        val tokenAuth =
            sharedPreferences.getString(CredentialsPreferences.TOKEN_AUTH, null) ?: run {
                onAuthFailed(TokenError.TOKEN_NOT_FOUND)
                return@tryAuth
            }

        if (System.currentTimeMillis() / 1000 - timestamp > ConstValue.TOKEN_AUTH_UPDATE_INTERVAL) {
            onAuthFailed(TokenError.TOKEN_NOT_VALIDATE)
            return
        }

        if (tokenAuth == "null" || tokenAuth == "") {
            onAuthFailed(TokenError.TOKEN_NOT_FOUND)
        } else {
            onAuthSuccess(tokenAuth)
        }
    }

    private fun initViews(tokenAuth: String) {
        binding?.apply {
            btnChangePassword.setOnClickListener {
                val oldPassword = editOldPassword.text.toString()
                val newPassword = editNewPassword.text.toString()
                val checkNewPassword = editCheckNewPassword.text.toString()

                if (oldPassword.isEmpty() || newPassword.isEmpty() || checkNewPassword.isEmpty()) {
                    Toast.makeText(
                        context,
                        R.string.fields_empty,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return@setOnClickListener
                }
                if (oldPassword != newPassword) {
                    if (newPassword == checkNewPassword) {
                        viewModel.changePassword(
                            "Bearer $tokenAuth", ChangePasswordRequest(
                                newPassword = newPassword,
                                oldPassword = oldPassword
                            )
                        )
                    } else {
                        Toast.makeText(
                            context,
                            R.string.new_passwords_do_not_match,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@setOnClickListener
                    }
                } else {
                    Toast.makeText(
                        context,
                        R.string.check_old_and_new_pass,
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
            viewModel.mesChangePasswordDataState.observe(viewLifecycleOwner) { passChangeStatusMessageDataModel ->
                passChangeStatusMessageDataModel?.let { data ->
                    when (data.message) {
                        STATUS_SUCCESSFULLY -> {
                            Toast.makeText(
                                context,
                                R.string.status_successfully_pass,
                                Toast.LENGTH_SHORT
                            ).show()
                            changeCredentialsPreferences()
                            (requireActivity() as? MainActivity)?.changeBtnNavVisibility(false)
                            onDestroyView()
                            findNavController().setGraph(R.navigation.auth_graph)
                        }

                        STATUS_FAILURE -> {
                            Toast.makeText(
                                context,
                                R.string.status_failure,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
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

    private fun onAuthSuccess(
        tokenAuth: String,
    ) {
        initViews(tokenAuth)
        observerData()
    }

    private fun displayErrorToast(@StringRes error: Int) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun changeCredentialsPreferences() {
        with(CredentialsPreferences.getCredentialsPreferences(requireContext()).edit()) {
            putLong(CredentialsPreferences.TIMESTAMP_TOKEN_AUTH, 0L)
            putString(CredentialsPreferences.TOKEN_AUTH, "")
            apply()
        }
    }
}