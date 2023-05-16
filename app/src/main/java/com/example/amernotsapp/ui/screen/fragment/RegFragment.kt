package com.example.amernotsapp.ui.screen.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.amernotsapp.AmernotsAppAplication
import com.example.amernotsapp.R
import com.example.amernotsapp.data.model.request.RegRequest
import com.example.amernotsapp.databinding.FragmentRegBinding
import com.example.amernotsapp.di.appComponent
import com.example.amernotsapp.di.lazyViewModel
import com.example.amernotsapp.ui.model.response.TokenAuthDataModel
import com.example.amernotsapp.ui.vm.RegFragmentViewModel
import retrofit2.HttpException

class RegFragment : Fragment(R.layout.fragment_reg) {

    private var binding: FragmentRegBinding? = null

    private val viewModel: RegFragmentViewModel by lazyViewModel {
        requireContext().appComponent().regViewModel().create(assistedValue = "AssistedValue")
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as? AmernotsAppAplication)?.appComponent?.inject(fragment = this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegBinding.bind(view)
        initViews()
        observerData()
    }

    private fun initViews() {
        binding?.apply {
            checkboxOne.setOnClickListener {
                if (checkboxOne.isChecked) {
                    radioGroup.visibility = View.VISIBLE
                } else {
                    radioGroup.visibility = View.GONE
                    rBtnOne.isChecked = false
                    rBtnTwo.isChecked = false
                    rBtnThree.isChecked = false
                }
            }

            btnRegNewUser.setOnClickListener {
                val userName = editNewUserName.text.toString()
                val userLogin = editNewUserLogin.text.toString()
                val userPassword = editNewUserPassword.text.toString()
                val userCheckPassword = editNewUserCheckPassword.text.toString()
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
                    if (userPassword == userCheckPassword) {
                        viewModel.requestTokenAuthByRegistration(
                            RegRequest(
                                login = userLogin,
                                password = userPassword,
                                username = userName,
                                userStatus = checkUserStatus() // todo: добавить метод что проверяет статус юзера
                            )
                        )
                        //findNavController().navigate(R.id.action_regFragment_to_authFragment)
                    } else {
                        Toast.makeText(
                            context,
                            "Passwords do not match",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        return@setOnClickListener
                    }
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
            viewModel.regNewUserDataState.observe(viewLifecycleOwner) {TokenAuthDataModel ->
                TokenAuthDataModel?.let {data ->
                    Toast.makeText(
                        requireContext(),
                        data.token,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            viewModel.errorState.observe(viewLifecycleOwner) {ex ->
                ex?.let {
                    val errorMessage =(ex as? HttpException)?.message() ?: ex.toString()
                    Toast.makeText(
                        requireContext(),
                        errorMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun checkUserStatus(): Int {
        var userStatus = 0
        binding?.apply {
            if (checkboxOne.isChecked){
                if (rBtnOne.isChecked){
                    userStatus = 1
                } else if (rBtnTwo.isChecked){
                    userStatus = 2
                } else if (rBtnThree.isChecked){
                    userStatus = 3
                }
            } else {
                userStatus = 0
            }

        }
        return userStatus
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}