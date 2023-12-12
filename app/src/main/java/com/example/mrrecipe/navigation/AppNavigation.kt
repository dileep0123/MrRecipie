package com.example.mrrecipe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mrrecipe.screens.addRecipe.AddRecipeUI
import com.example.mrrecipe.screens.addRecipe.AddRecpieViewModel
import com.example.mrrecipe.screens.dashboard.DashboardScreen
import com.example.mrrecipe.screens.dashboard.DashboardViewModel
import com.example.mrrecipe.screens.loginSignup.LoginScreen
import com.example.mrrecipe.screens.loginSignup.LoginViewModel
import com.example.mrrecipe.screens.loginSignup.SignupScreen

@Composable
fun StartAppNavigation(screens: Screens) {
	val navController = rememberNavController()

	NavHost(navController = navController, startDestination = screens.name) {

		composable(Screens.LOGIN.name) {
			val loginViewModel = viewModel<LoginViewModel>()

			LoginScreen(
				{

					navController.navigate(Screens.DASHBOARD.name) {
						popUpTo(navController.graph.id) {
							inclusive = true
						}
					}

				},
				{ navController.navigate(Screens.SIGNUP.name) },
				loginViewModel,
				loginViewModel.loginUIState.collectAsState()
			)
		}
		composable(Screens.SIGNUP.name) {
			val loginViewModel = viewModel<LoginViewModel>()

			SignupScreen({
				navController.popBackStack()
			}, loginViewModel, loginViewModel.signupUIState.collectAsState())
		}
		composable(Screens.DASHBOARD.name) {
			val dashbordVM = viewModel<DashboardViewModel>()
			DashboardScreen(dashbordVM, dashbordVM.dashBoardIState.collectAsState(), {
				navController.navigate(Screens.ADD_RECIPE.name)
			}, {
				dashbordVM.logoutUser()
				navController.navigate(Screens.LOGIN.name) {
					popUpTo(navController.graph.id) {
						inclusive = true
					}
				}

			}, heartButtonClick = { itemId, isFav ->
				dashbordVM.updateUserData(itemId, isFav)
				dashbordVM.fetchData()
			}, deleteButtonClick = { itemId, isFav ->
				dashbordVM.dislikeUserData(itemId, isFav)
				dashbordVM.fetchData()
			})
		}

		composable(Screens.ADD_RECIPE.name) {

			val addRecpieViewModel = viewModel<AddRecpieViewModel>()
			AddRecipeUI(
				addRecpieViewModel,
				addRecpieViewModel.addRecipeUIState.collectAsState(),
				addRecpieViewModel.forValidationUIState.collectAsState(),
				addRecpieViewModel.pickerState.collectAsState()
			) { navController.popBackStack() }
		}
	}

}


enum class Screens {
	LOGIN, SIGNUP, DASHBOARD, ADD_RECIPE
}