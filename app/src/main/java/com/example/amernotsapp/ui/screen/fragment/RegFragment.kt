package com.example.amernotsapp.ui.screen.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.amernotsapp.R
import com.example.amernotsapp.databinding.FragmentRegBinding

class RegFragment : Fragment(R.layout.fragment_reg) {

    private var binding: FragmentRegBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegBinding.bind(view)

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
                        findNavController().navigate(R.id.action_regFragment_to_authFragment)
                    } else {
                        Toast.makeText(context,
                            "Passwords do not match",
                            Toast.LENGTH_SHORT)
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

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}