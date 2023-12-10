package com.example.mrrecipe.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mrrecipe.screens.AddRecipeUI
import com.example.mrrecipe.screens.AlertErroDialog
import com.example.mrrecipe.screens.dashboard.DashboardScreen
import com.example.mrrecipe.screens.loginSignup.LoginScreen
import com.example.mrrecipe.screens.loginSignup.LoginViewModel
import com.example.mrrecipe.screens.loginSignup.SignupScreen

@Composable
fun StartAppNavigation(screens: Screens) {
	val navController = rememberNavController()
	val loginViewModel = viewModel<LoginViewModel>()

	NavHost(navController = navController, startDestination = screens.name) {

		composable(Screens.LOGIN.name) {
			LoginScreen(
				{ navController.navigate(Screens.DASHBOARD.name) },
				{ navController.navigate(Screens.SIGNUP.name) }, loginViewModel
			)
		}
		composable(Screens.SIGNUP.name) {
			SignupScreen({
				navController.popBackStack()
			}, loginViewModel)
		}
		composable(Screens.DASHBOARD.name) {
			DashboardScreen { navController.navigate(Screens.ADD_RECIPE.name) }
		}

		composable(Screens.ADD_RECIPE.name) {
			AddRecipeUI { navController.popBackStack() }
		}
	}

}


enum class Screens {
	LOGIN, SIGNUP, DASHBOARD, ADD_RECIPE
}