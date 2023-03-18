package com.example.amernotsapp.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.amernotsapp.R
import com.example.amernotsapp.databinding.FragmentMainBinding

class MainFragment: Fragment(R.layout.fragment_main) {

    private var binding: FragmentMainBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)

        binding?.apply {
            tvExampleText.text = getString(R.string.example_text)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}