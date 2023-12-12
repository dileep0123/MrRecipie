package com.example.mrrecipe

data class RecipeData(
	val title: String = "",
	val description: String = "",
	val imgUrl: String = "",
	var favourite: Boolean = false,
	var ingrediants: List<String>,
	var dislikedUsers: List<String>,
	var likedUsers: List<String>
)
