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
import com.example.amernotsapp.ui.enums.TokenError
import com.example.amernotsapp.ui.preferences.CredentialsPreferences
import com.example.amernotsapp.ui.screen.activity.MainActivity
import com.example.amernotsapp.ui.vm.AdditInfoNewsFragmentViewModel

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

    private fun onAuthFailed(error: TokenError) {  // вернёт сообщение о не валидности токена и попросит снова авторизоваться
        when (error) {
            TokenError.TOKEN_NOT_FOUND -> {
                displayErrorToast(R.string.unknown_error)
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
        }
    }

    private fun onAuthSuccess(
        tokenAuth: String,
        newsId: String,
    ) { // добавит в запрос header и добавит значения во вьюхи
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

    companion object {
        const val TOKEN_AUTH_UPDATE_INTERVAL = 60 // в рамках теста значение равно 60с (макс знач 24ч) изменю до 5 часов
        const val ROLE_FIRE_DEPARTMENT = "ROLE_FIRE_DEPARTMENT"
        const val ROLE_AMBULANCE = "ROLE_AMBULANCE"
        const val ROLE_POLICE = "ROLE_POLICE"
        const val ROLE_USER = "ROLE_USER"
    }
}