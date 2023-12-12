package com.example.mrrecipe.screens.addRecipe

data class FormValidationData(
	val isValidated: Boolean = false,
	val message: String = "",
	val isLoading: Boolean = false,
	val isSuccess: Boolean = false,
	val isFailure: Boolean = false
)
