package com.example.amernotsapp.ui.screen.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.amernotsapp.AmernotsAppAplication
import com.example.amernotsapp.BuildConfig
import com.example.amernotsapp.R
import com.example.amernotsapp.data.api.model.request.AddNewsRequest
import com.example.amernotsapp.data.api.model.request.DaDataCheckAddressRequest
import com.example.amernotsapp.databinding.FragmentFormCreateNewsBinding
import com.example.amernotsapp.di.appComponent
import com.example.amernotsapp.di.lazyViewModel
import com.example.amernotsapp.ui.enums.ConstValue
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_AMBULANCE
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_FIRE_DEPARTMENT
import com.example.amernotsapp.ui.enums.ConstValue.Companion.ROLE_POLICE
import com.example.amernotsapp.ui.enums.ConstValue.Companion.STATUS_SUCCESSFULLY
import com.example.amernotsapp.ui.enums.TokenError
import com.example.amernotsapp.ui.preferences.CredentialsPreferences
import com.example.amernotsapp.ui.screen.activity.MainActivity
import com.example.amernotsapp.ui.vm.AddNewsFragmentViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddNewsFragment : Fragment(R.layout.fragment_form_create_news) {

    private var binding: FragmentFormCreateNewsBinding? = null

    private val viewModel: AddNewsFragmentViewModel by lazyViewModel {
        requireContext().appComponent().addNewsViewModel().create(assistedValue = "AssistedValue")
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as? AmernotsAppAplication)?.appComponent?.injectAddNews(
            fragment = this
        )
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(false)
        binding = FragmentFormCreateNewsBinding.bind(view)
        setTextWatchers()


        tryAuth()
    }

    override fun onDestroyView() {
        (requireActivity() as? MainActivity)?.changeBtnNavVisibility(true)
        binding = null
        super.onDestroyView()
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

    private fun checkAddress(value: CharSequence) {
        binding?.apply {
            viewModel.requestCheckAddress(
                BuildConfig.API_KEY_DA_DATA,
                DaDataCheckAddressRequest(
                    value.toString()
                )
            )

            viewModel.checkAddressDataState.observe(viewLifecycleOwner) { daDataSuggestionsDataModel ->
                daDataSuggestionsDataModel?.let { data ->
                    val items = data.suggestions
                    val itemsValue = items.map { item ->
                        item.value
                    }
                    val itemsUnrestrictedValue = items.map { item ->
                        item.unrestrictedValue
                    }
                    val itemsAdapter = itemsValue + itemsUnrestrictedValue
                    val adapter = ArrayAdapter<String>(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        itemsAdapter
                    )
                    editNewAddress.setAdapter(adapter)
                }
            }
        }

    }

    private fun initViews(tokenAuth: String) {
        binding?.apply {
            btnCreateNews.setOnClickListener {
                val tittleSituation = editNewTittleSituation.text.toString()
                val description = editNewDescription.text.toString()


                val address = editNewAddress.text.toString()
                if (checkNewsRole() == getString(R.string.check_r_btn) || checkUrgencyCode() == 0) {
                    Toast.makeText(
                        context,
                        R.string.check_r_btn,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                viewModel.requestAddNews(
                    tokenAuth = "Bearer $tokenAuth",
                    addNewsRequest = AddNewsRequest(
                        tittleSituation = tittleSituation,
                        description = description,
                        address = address,
                        timeRelease = getTimeRelease(),
                        urgencyCode = checkUrgencyCode(),
                        roleNews = checkNewsRole()
                    )
                )
            }
        }
    }

    private fun observerData() {
        binding?.apply {
            viewModel.addNewsDataState.observe(viewLifecycleOwner) { changeStatusMessageDataModel ->
                changeStatusMessageDataModel?.let { data ->
                    when (data.message) {
                        STATUS_SUCCESSFULLY -> {
                            Toast.makeText(
                                context,
                                R.string.status_successfully_create,
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().navigate(R.id.action_addNewsFragment_to_newslineFragment)
                        }

                        else -> {
                            Toast.makeText(
                                context,
                                R.string.status_failure,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
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

    private fun getTimeRelease(): String {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(calendar.time)

        return "$time $day.$month.$year"
    }


    private fun checkNewsRole(): String {
        var userStatus = ""
        binding?.apply {
            if (rBtnFireDepartmentNew.isChecked) {
                userStatus = ROLE_FIRE_DEPARTMENT
            } else if (rBtnAmbulanceNew.isChecked) {
                userStatus = ROLE_AMBULANCE
            } else if (rBtnPoliceNew.isChecked) {
                userStatus = ROLE_POLICE
            }
        }
        return if (userStatus == "") {
            getString(R.string.check_r_btn)
        } else {
            userStatus
        }

    }

    private fun checkUrgencyCode(): Int {
        var urgencyCode = 0
        binding?.apply {
            if (rBtnOne.isChecked) {
                urgencyCode = 1
            } else if (rBtnTwo.isChecked) {
                urgencyCode = 2
            } else if (rBtnThree.isChecked) {
                urgencyCode = 3
            } else if (rBtnFour.isChecked) {
                urgencyCode = 4
            } else if (rBtnFive.isChecked) {
                urgencyCode = 5
            }
        }
        return if (urgencyCode == 0) {
            0
        } else {
            urgencyCode
        }
    }

    private fun displayErrorToast(@StringRes error: Int) {
        Toast.makeText(
            requireContext(),
            error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun setTextWatchers() {

        val textWatcherEmpty: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkSetTextButton()
            }
            override fun afterTextChanged(s: Editable) {}
        }

        val textWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkAddress(s)
            }
            override fun afterTextChanged(s: Editable) {
            }
        }


        binding?.apply {
            editNewAddress.addTextChangedListener(textWatcher)
            editNewAddress.addTextChangedListener(textWatcherEmpty)
            editNewDescription.addTextChangedListener(textWatcherEmpty)
            editNewTittleSituation.addTextChangedListener(textWatcherEmpty)
        }
    }

    private fun checkSetTextButton() {
        binding?.apply {
            btnCreateNews.isEnabled = !editNewDescription.text.isNullOrBlank() &&
                    !editNewTittleSituation.text.isNullOrBlank() && !editNewAddress.text.isNullOrBlank()
        }
    }

}