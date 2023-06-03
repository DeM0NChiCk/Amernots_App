package com.example.amernotsapp.ui.screen.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.NavigationRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.amernotsapp.R
import com.example.amernotsapp.databinding.ActivityMainBinding
import com.example.amernotsapp.ui.enums.ConstValue.Companion.TOKEN_AUTH_UPDATE_INTERVAL
import com.example.amernotsapp.ui.preferences.CredentialsPreferences


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val fragment by lazy {
        supportFragmentManager
            .findFragmentById(R.id.container) as NavHostFragment
    }

    override fun onStart() {
        super.onStart()

        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(applicationContext)
        val theme = sharedPreferences.getString("theme", "") ?: return
        setTheme(theme)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        applicationContext.deleteDatabase("token_auth_cache_db")

        _binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            bottomNavigationFragmentMain.setupWithNavController(
                fragment.navController
            )

            setContentView(root)

            tryAuth()
        }
    }

    private fun tryAuth() {
        val sharedPreferences = CredentialsPreferences.getCredentialsPreferences(applicationContext)

        val timestamp = sharedPreferences.getLong(CredentialsPreferences.TIMESTAMP_TOKEN_AUTH, 0L)
        val tokenAuth = sharedPreferences.getString(CredentialsPreferences.TOKEN_AUTH, null) ?: run {
            setNavGraph(R.navigation.auth_graph)
            return@tryAuth
        }

        if (System.currentTimeMillis() / 1000 - timestamp > TOKEN_AUTH_UPDATE_INTERVAL) {
            setNavGraph(R.navigation.auth_graph)
            return
        }

        setNavGraph(R.navigation.bottom_graph)
        changeBtnNavVisibility(true)

    }

    private fun setNavGraph(@NavigationRes graphId: Int) {
        fragment.navController.setGraph(graphId)
    }

    fun changeBtnNavVisibility(isVisible: Boolean) {
        binding.bottomNavigationFragmentMain.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    companion object {
        fun setTheme(theme: String) {
            when (theme) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else -> AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                )
            }
        }
    }
}