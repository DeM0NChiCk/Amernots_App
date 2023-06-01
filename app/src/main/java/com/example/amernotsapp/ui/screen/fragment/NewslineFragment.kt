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
import com.example.amernotsapp.databinding.FragmentNewslineBinding
import com.example.amernotsapp.di.appComponent
import com.example.amernotsapp.di.lazyViewModel
import com.example.amernotsapp.ui.enums.TokenError
import com.example.amernotsapp.ui.preferences.CredentialsPreferences
import com.example.amernotsapp.ui.recyclers.newsline.NewslineAdapter
import com.example.amernotsapp.ui.screen.activity.MainActivity
import com.example.amernotsapp.ui.vm.NewslineFragmnetViewModel

class NewslineFragment: Fragment(R.layout.fragment_newsline) {

    private var binding: FragmentNewslineBinding? = null

    private val viewModel:NewslineFragmnetViewModel by lazyViewModel {
        requireContext().appComponent().newslineViewModel().create(assistedValue = "AssistedValue")
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as? AmernotsAppAplication)?.appComponent?.injectNewsline(fragment = this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewslineBinding.bind(view)

        val token_auth = getTokenAuth()

        val timestamp = getTimastamp()

        if (token_auth != "null") {
            if (System.currentTimeMillis() / 1000 - timestamp < TOKEN_UPDATE_INTERVAL) {
                onAuthSuccess(token_auth.toString())
            } else onAuthFailed(TokenError.TOKEN_NOT_VALIDATE)
        } else onAuthFailed(TokenError.TOKEN_NOT_FOUND)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun getTimastamp(): Long {
        val sharedPreferences = CredentialsPreferences.getCredentialsPreferences(requireContext())

        return sharedPreferences.getLong(CredentialsPreferences.TIMESTAMP_TOKEN_AUTH, 0L)
    }

    private fun getTokenAuth(): String? {
        val sharedPreferences = CredentialsPreferences.getCredentialsPreferences(requireContext())

        return sharedPreferences.getString(CredentialsPreferences.TOKEN_AUTH, "null")
    }

    private fun initViews(tokenAuth: String) {
        binding?.apply {
            viewModel.requestGetNewsline("Bearer $tokenAuth")
        }
    }

    private fun observerData() {
        binding?.apply {
            viewModel.getNewslineDataState.observe(viewLifecycleOwner) { NewslineDataModel ->
                NewslineDataModel?.let { data ->
                    recyclerViewNewsline.adapter = NewslineAdapter(data)
                    if (data.userStatus == ROLE_USER) {
                        btnAddNews.visibility = View.VISIBLE
                    } else if (data.userStatus == ROLE_AMBULANCE || data.userStatus == ROLE_POLICE || data.userStatus == ROLE_FIRE_DEPARTMENT) {
                        btnAddNews.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun onAuthSuccess(tokenAuth: String) { // добавит в запрос header и добавит значения во вьюхи
        initViews(tokenAuth)
        observerData()
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

    private fun displayErrorToast(@StringRes error: Int) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        const val TOKEN_UPDATE_INTERVAL = 60 // в рамках теста значение равно 60с (макс знач 24ч) изменю до 5 часов
        const val ROLE_USER = "ROLE_USER"
        const val ROLE_FIRE_DEPARTMENT = "ROLE_FIRE_DEPARTMENT"
        const val ROLE_AMBULANCE = "ROLE_AMBULANCE"
        const val ROLE_POLICE = "ROLE_POLICE"
    }
}