package com.example.amernotsapp.ui.screen.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.amernotsapp.R
import com.example.amernotsapp.databinding.FragmentAuthBinding

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var binding: FragmentAuthBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)



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
                    findNavController()
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
            btnSingIn.setOnClickListener {
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}