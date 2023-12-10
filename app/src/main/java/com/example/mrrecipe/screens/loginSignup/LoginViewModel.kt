package com.example.mrrecipe.screens.loginSignup

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginViewModel : ViewModel() {

	private lateinit var auth: FirebaseAuth

	var username by mutableStateOf("")

	fun updateUsername(input: String) {
		username = input
		Log.e("in","-- $username")
	}

	var password by mutableStateOf("")

	fun updatePassword(input: String) {
		password = input
	}

	var confirmPassword by mutableStateOf("")

	fun updateConfirmPassword(input: String) {
		confirmPassword = input
	}

	init {
		auth = Firebase.auth
	}

	fun signupUser() {

	}
}