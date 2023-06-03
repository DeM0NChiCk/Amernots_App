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
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_AMBULANCE
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_FIRE_DEPARTMENT
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_POLICE
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_USER
import com.example.amernotsapp.ui.enums.ConstValue.Companion.TOKEN_AUTH_UPDATE_INTERVAL
import com.example.amernotsapp.ui.enums.TokenError
import com.example.amernotsapp.ui.preferences.CredentialsPreferences
import com.example.amernotsapp.ui.recyclers.newsline.NewslineAdapter
import com.example.amernotsapp.ui.screen.activity.MainActivity
import com.example.amernotsapp.ui.vm.NewslineFragmnetViewModel
import retrofit2.HttpException

class NewslineFragment : Fragment(R.layout.fragment_newsline) {

    private var binding: FragmentNewslineBinding? = null

    private val viewModel: NewslineFragmnetViewModel by lazyViewModel {
        requireContext().appComponent().newslineViewModel().create(assistedValue = "AssistedValue")
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as? AmernotsAppAplication)?.appComponent?.injectNewsline(
            fragment = this
        )
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewslineBinding.bind(view)

        tryAuth()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun tryAuth() {

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
            onAuthSuccess(tokenAuth)
        }
    }

    private fun initViews(tokenAuth: String) {
        binding?.apply {
            viewModel.requestGetNewsline("Bearer $tokenAuth")
        }
    }

    private fun observerData() {
        binding?.apply {
            viewModel.getNewslineDataState.observe(viewLifecycleOwner) { newslineDataModel ->
                newslineDataModel?.let { data ->
                    recyclerViewNewsline.adapter = NewslineAdapter(data, findNavController())
                    if (data.userStatus == ROLE_USER) {
                        btnAddNews.visibility = View.VISIBLE
                    } else if (data.userStatus == ROLE_AMBULANCE || data.userStatus == ROLE_POLICE || data.userStatus == ROLE_FIRE_DEPARTMENT) {
                        btnAddNews.visibility = View.GONE
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

    private fun onAuthSuccess(tokenAuth: String) { // добавит в запрос header и добавит значения во вьюхи
        initViews(tokenAuth)
        observerData()
    }

    private fun onAuthFailed(error: TokenError) {  // вернёт сообщение о не валидности токена и попросит снова авторизоваться
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

    private fun displayErrorToast(@StringRes error: Int) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }


}