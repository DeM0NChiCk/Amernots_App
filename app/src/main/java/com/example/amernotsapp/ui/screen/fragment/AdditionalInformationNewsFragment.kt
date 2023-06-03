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
import com.example.amernotsapp.databinding.FragmentAdditionalInformationNewsBinding
import com.example.amernotsapp.di.appComponent
import com.example.amernotsapp.di.lazyViewModel
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_AMBULANCE
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_FIRE_DEPARTMENT
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_POLICE
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_USER
import com.example.amernotsapp.ui.enums.ConstValue.Companion.STATUS_FAILURE
import com.example.amernotsapp.ui.enums.ConstValue.Companion.STATUS_SOMEONE_ANSWERED
import com.example.amernotsapp.ui.enums.ConstValue.Companion.STATUS_SUCCESSFULLY
import com.example.amernotsapp.ui.enums.ConstValue.Companion.TOKEN_AUTH_UPDATE_INTERVAL
import com.example.amernotsapp.ui.enums.TokenError
import com.example.amernotsapp.ui.preferences.CredentialsPreferences
import com.example.amernotsapp.ui.screen.activity.MainActivity
import com.example.amernotsapp.ui.vm.AdditInfoNewsFragmentViewModel
import retrofit2.HttpException

class AdditionalInformationNewsFragment : Fragment(R.layout.fragment_additional_information_news) {

    private var binding: FragmentAdditionalInformationNewsBinding? = null

    private val viewModel: AdditInfoNewsFragmentViewModel by lazyViewModel {
        requireContext().appComponent().additInfoNewsViewModel()
            .create(assistedValue = "AssistedValue")
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as? AmernotsAppAplication)?.appComponent?.injectAdditionalInfoNews(
            fragment = this
        )
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdditionalInformationNewsBinding.bind(view)
        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(false)


        val newsId = arguments?.getString("newsId", null)?: return

        tryAuth(newsId)
    }

    override fun onDestroyView() {
        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(true)
        super.onDestroyView()
        binding = null
    }

    private fun tryAuth(newsId: String) {

        val sharedPreferences = CredentialsPreferences.getCredentialsPreferences(requireContext())

        val timestamp = sharedPreferences.getLong(CredentialsPreferences.TIMESTAMP_TOKEN_AUTH, 0L)
        val tokenAuth =
            sharedPreferences.getString(CredentialsPreferences.TOKEN_AUTH, null) ?: run {
                onAuthFailed(TokenError.TOKEN_NOT_FOUND)
                return@tryAuth
            }

        if (System.currentTimeMillis() / 1000 - timestamp > TOKEN_AUTH_UPDATE_INTERVAL) {
            onAuthFailed(TokenError.TOKEN_NOT_VALIDATE)
            return
        }

        if (tokenAuth == "null" || tokenAuth == "") {
            onAuthFailed(TokenError.TOKEN_NOT_FOUND)
        } else {
            onAuthSuccess(tokenAuth, newsId)
        }
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


    private fun initViews(tokenAuth: String, newsId: String) {
        binding?.apply {
            btnTakeTheChallenge.setOnClickListener {
                viewModel.requestChangeNewsStatus("Bearer $tokenAuth", newsId)
            }

            viewModel.requestGetNewsById("Bearer $tokenAuth", newsId)
        }
    }

    private fun observerData() {
        binding?.apply {
            viewModel.getNewsByIdDataState.observe(viewLifecycleOwner) { NewsByIdDataModel ->
                NewsByIdDataModel?.let { data ->
                    tvTitleSituation.text =
                        getString(R.string.title_situation, data.tittleSituation)
                    tvDescription.text = getString(R.string.description, data.description)
                    tvTimeRelease.text = getString(R.string.time_release, data.timeRelease)
                    tvAddress.text = getString(R.string.address, data.address)
                    when (data.roleNews) {
                        ROLE_POLICE -> {
                            tvRoleNews.text = getString(R.string.role_news, "Полиции")
                        }

                        ROLE_AMBULANCE -> {
                            tvRoleNews.text = getString(R.string.role_news, "Медицинской службы")
                        }

                        ROLE_FIRE_DEPARTMENT -> {
                            tvRoleNews.text =
                                getString(R.string.role_news, "МЧС или Пожарной службы")
                        }
                    }
                    when (data.urgencyCode) {
                        5 -> {
                            tvUrgencyCode.text = getString(R.string.urgency_code, "5")
                        }

                        4 -> {
                            tvUrgencyCode.text = getString(R.string.urgency_code, "4")
                        }

                        3 -> {
                            tvUrgencyCode.text = getString(R.string.urgency_code, "3")
                        }

                        2 -> {
                            tvUrgencyCode.text = getString(R.string.urgency_code, "2")
                        }

                        1 -> {
                            tvUrgencyCode.text = getString(R.string.urgency_code, "1")
                        }
                    }
                    when (data.userStatus) {
                        ROLE_USER -> {
                            btnTakeTheChallenge.visibility = View.GONE
                        }
                        else -> {
                            btnTakeTheChallenge.visibility = View.VISIBLE
                        }
                    }
                    if (data.employeeId != -1L) {
                        btnTakeTheChallenge.visibility = View.GONE
                    }
                }
            }

            viewModel.changeNewsStatusDataState.observe(viewLifecycleOwner) { changeStatusMessageDataModel ->
                changeStatusMessageDataModel?.let { data ->
                    when (data.message) {
                        STATUS_SUCCESSFULLY -> {
                            btnTakeTheChallenge.visibility = View.GONE
                            Toast.makeText(
                                context,
                                R.string.status_successfully,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        STATUS_FAILURE -> {
                            Toast.makeText(
                                context,
                                R.string.status_failure,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        STATUS_SOMEONE_ANSWERED -> {
                            btnTakeTheChallenge.visibility = View.GONE
                            Toast.makeText(
                                context,
                                R.string.status_someone_answered,
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
        newsId: String,
    ) {
        initViews(tokenAuth, newsId)
        observerData()
    }

    private fun displayErrorToast(@StringRes error: Int) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }
}