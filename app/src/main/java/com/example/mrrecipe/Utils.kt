package com.example.mrrecipe

import android.util.Patterns

object Utils {

	const val DB_NAME = "Users"
	const val DB_NAME_RECIPE = "USERS_RECIPE"

	fun isValidEmail(email: String): Boolean {
		return Patterns.EMAIL_ADDRESS.matcher(email).matches()
	}
}