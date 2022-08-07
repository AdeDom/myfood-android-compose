package com.adedom.myfood.presentation.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.adedom.myfood.base.BaseActivity
import com.adedom.myfood.databinding.ActivityMainBinding
import com.adedom.myfood.presentation.main.event.MainUiEvent
import com.adedom.myfood.presentation.main.state.MainUiState
import com.adedom.myfood.presentation.main.view_model.MainViewModel
import com.adedom.myfood.presentation.welcome.view.WelcomeActivity
import kotlinx.coroutines.launch
import org.kodein.di.instance

class MainActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is MainUiState.Initial -> {
                        }
                        is MainUiState.ShowUserProfile -> {
                            binding.tvUserId.text = uiState.userProfile.userId
                            binding.tvEmail.text = uiState.userProfile.email
                            binding.tvName.text = uiState.userProfile.name
                            binding.tvMobileNo.text = uiState.userProfile.mobileNo
                            binding.tvAddress.text = uiState.userProfile.address
                            binding.tvImage.text = uiState.userProfile.image
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { uiEvent ->
                    when (uiEvent) {
                        MainUiEvent.Logout -> {
                            val intent = Intent(baseContext, WelcomeActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.refreshTokenExpired.collect { baseError ->
                    val errorMessage = baseError.message
                    Toast.makeText(baseContext, errorMessage, Toast.LENGTH_SHORT).show()
                    val intent = Intent(baseContext, WelcomeActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }

        viewModel.callApiService()

        binding.btLogout.setOnClickListener {
            viewModel.callLogout()
            viewModel.onLogoutEvent()
        }
    }
}