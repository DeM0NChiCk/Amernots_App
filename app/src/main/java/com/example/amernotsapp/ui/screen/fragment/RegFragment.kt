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
import com.example.amernotsapp.ui.vm.RegFragmentViewModel

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
                                userStatus = 0 // todo: добавить метод что проверяет статус юзера
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

    }

    private fun chechUserStatus(): Int{
        binding?.apply {

        }
        return 0
    }


//        binding?.apply {
//            }
//            checkboxOne.setOnClickListener {
//                if (checkboxOne.isChecked) {
//                    radioGroup.visibility = View.VISIBLE
//                } else {
//                    radioGroup.visibility = View.GONE
//                    rBtnOne.isChecked = false
//                    rBtnTwo.isChecked = false
//                    rBtnThree.isChecked = false
//                }
//            }
//
//        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}