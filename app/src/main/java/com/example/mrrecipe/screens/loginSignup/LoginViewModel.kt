package com.example.mrrecipe.screens.loginSignup

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mrrecipe.NetworkResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

	private var auth: FirebaseAuth

	init {
		auth = Firebase.auth

		var currentUser = auth.currentUser?.email
		Log.e("CURRENT ", "USER SI $currentUser")
	}

	private val _signupUIState = MutableStateFlow(SignUpUIState())
	internal val signupUIState = _signupUIState.asStateFlow()

	private val _loginUIState = MutableStateFlow(LoginUIState())
	internal val loginUIState = _loginUIState.asStateFlow()


	fun updateUsername(input: String) {
		_signupUIState.update { _signupUIState.value.copy(username = input) }
		_loginUIState.update { _loginUIState.value.copy(username = input) }
		Log.e("Item ", "FROM VIEWMODEL $input -- ${signupUIState.value.username}")

	}

	fun updatePassword(input: String) {
		_signupUIState.update { _signupUIState.value.copy(password = input) }
		_loginUIState.update { _loginUIState.value.copy(password = input) }

	}

	fun updateConfirmPassword(input: String) {
		_signupUIState.update { _signupUIState.value.copy(confirmPassword = input) }
	}


	fun signupUser() {

		val username = signupUIState.value.username
		val password = signupUIState.value.password

		_signupUIState.update { _signupUIState.value.copy(isLoading = true) }


		auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener() { task ->
			if (task.isSuccessful) {
				Log.e("Sign UP ", "Success ${auth.currentUser?.email}")
				_signupUIState.update { _signupUIState.value.copy(isLoading = false) }
				_signupUIState.update {
					_signupUIState.value.copy(
						networkResult = NetworkResult(
							isSuccess = true,
							message = "Signup Success."
						)
					)
				}

			} else {
				Log.e("Sign UP ", "Failed ${task.exception?.message}")
				_signupUIState.update { _signupUIState.value.copy(isLoading = false) }
				_signupUIState.update {
					_signupUIState.value.copy(
						networkResult = NetworkResult(
							isSuccess = false,
							message = "Signup Failure.\n${task.exception?.message}"
						)
					)
				}
			}
		}
	}

	fun loginUser() {
		_loginUIState.update { _loginUIState.value.copy(isLoading = true) }
		auth.signInWithEmailAndPassword(loginUIState.value.username, loginUIState.value.password)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					_loginUIState.update { _loginUIState.value.copy(isLoading = false) }
					_loginUIState.update {
						_loginUIState.value.copy(
							networkResult = NetworkResult(
								isSuccess = true,
								message = "Login Success."
							)
						)
					}
				} else {
					_loginUIState.update { _loginUIState.value.copy(isLoading = false) }
					_loginUIState.update {
						_loginUIState.value.copy(
							networkResult = NetworkResult(
								isSuccess = false,
								message = "Login Failure.\n${task.exception?.message}"
							)
						)
					}
				}

			}
	}



	data class SignUpUIState(
		var username: String = "",
		var password: String = "",
		var confirmPassword: String = "",
		var networkResult: NetworkResult? = null,
		var isLoading: Boolean = false
	)

	data class LoginUIState(
		var username: String = "",
		var password: String = "",
		var networkResult: NetworkResult? = null,
		var isLoading: Boolean = false
	)
}