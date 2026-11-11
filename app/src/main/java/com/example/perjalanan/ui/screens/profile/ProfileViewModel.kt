package com.example.perjalanan.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perjalanan.data.AppContainer
import com.example.perjalanan.data.model.User
import com.example.perjalanan.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val user: User = User(),
    val isLogoutDialogOpen: Boolean = false,
    val isLoggedOut: Boolean = false
)

class ProfileViewModel(
    private val authRepository: AuthRepository = AppContainer.authRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            authRepository.currentUser.collect { user ->
                if (user != null) {
                    _uiState.update { it.copy(user = user) }
                }
            }
        }
    }

    fun openLogoutDialog() {
        _uiState.update { it.copy(isLogoutDialogOpen = true) }
    }

    fun closeLogoutDialog() {
        _uiState.update { it.copy(isLogoutDialogOpen = false) }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { it.copy(isLogoutDialogOpen = false, isLoggedOut = true) }
        }
    }
}
