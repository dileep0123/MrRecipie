package com.example.mrrecipe.screens.loginSignup


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrrecipe.Loader
import com.example.mrrecipe.R
import com.example.mrrecipe.Utils
import com.example.mrrecipe.screens.AlertErroDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
	signupButtonClick: () -> Unit,
	loginViewModel: LoginViewModel,
	loginState: State<LoginViewModel.SignUpUIState>
) {
	var isFormValidate by rememberSaveable { mutableStateOf(false) }
	var errorMessage by rememberSaveable { mutableStateOf("") }
	var errorOccured by rememberSaveable { mutableStateOf(true) }

	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Sign up",
						fontSize = 20.sp,
						fontWeight = FontWeight.Medium,
						textAlign = TextAlign.Center
					)
				},
				colors = TopAppBarDefaults.smallTopAppBarColors(
					containerColor = MaterialTheme.colorScheme.primary,
					titleContentColor = Color.White
				),

				)
		}
	) { paddingValues ->


		Column(
			modifier = Modifier
				.padding(paddingValues)
				.padding(30.dp)
				.fillMaxWidth()
				.fillMaxHeight(),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {


			Image(
				painter = painterResource(id = R.drawable.recipe_icon),
				contentDescription = "",
				modifier = Modifier.size(100.dp)
			)

			ElevatedCard(
				elevation = CardDefaults.cardElevation(
					defaultElevation = 6.dp
				),
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 15.dp),


				) {
				Column(
					Modifier
						.fillMaxWidth()
						.padding(15.dp), horizontalAlignment = Alignment.CenterHorizontally
				) {

					OutlinedTextField(

						label = { Text(text = "Enter Email") },
						value = loginState.value.username,
						onValueChange = {
							loginViewModel.updateUsername(it)
						},
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp),
						singleLine = true,
						maxLines = 1,
						keyboardOptions = KeyboardOptions(
							imeAction = ImeAction.Next, // ** Go to next **
							keyboardType = KeyboardType.Email
						)

					)
					OutlinedTextField(
						label = { Text(text = "Enter Password") },
						value = loginState.value.password,
						onValueChange = {
							loginViewModel.updatePassword(it)
						},
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp),
						singleLine = true,
						maxLines = 1,
						keyboardOptions = KeyboardOptions(
							imeAction = ImeAction.Next, // ** Go to next **
							keyboardType = KeyboardType.Password
						),
						visualTransformation = PasswordVisualTransformation()

					)

					OutlinedTextField(
						label = { Text(text = "Confirm Password") },
						value = loginState.value.confirmPassword,
						onValueChange = {
							loginViewModel.updateConfirmPassword(it)
						},
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp),
						singleLine = true,
						maxLines = 1,
						keyboardOptions = KeyboardOptions(
							imeAction = ImeAction.Done,
							keyboardType = KeyboardType.Password
						),
						visualTransformation = PasswordVisualTransformation()
					)

					ElevatedButton(
						onClick = {


							if (loginState.value.username.isEmpty()) {
								errorMessage = "Please enter email ."
								isFormValidate = true
							} else if (Utils.isValidEmail(loginState.value.username).not()) {
								errorMessage = "Please enter valid email ."
								isFormValidate = true
							} else if (loginState.value.password.isEmpty()) {
								errorMessage = "Please enter password ."
								isFormValidate = true
							} else if (loginState.value.password.length < 6) {
								errorMessage = "Password should be at least 6 characters."
								isFormValidate = true
							} else if (loginState.value.confirmPassword.isEmpty()) {
								errorMessage = "Please enter confirm password ."
								isFormValidate = true
							} else if (loginState.value.confirmPassword
									.equals(loginState.value.password, ignoreCase = false).not()
							) {
								errorMessage = "Password and Confirm password did not match ."
								isFormValidate = true
							} else {
								System.out.println("Ready to create account")
								errorOccured = true
								loginViewModel.signupUser()
							}


						}, colors = ButtonDefaults.elevatedButtonColors(
							containerColor = MaterialTheme.colorScheme.primary
						),
						modifier = Modifier.padding(top = 20.dp)
					) {
						Text(text = "SIGN UP", color = Color.White, fontWeight = FontWeight.Bold)
					}

				}
			}

			if (isFormValidate) {
				AlertErroDialog(dialogText = errorMessage) {
					isFormValidate = false
				}
			}

		}

	}

	if (loginState.value.isLoading) {
		Loader()
	}



	if (loginState.value.networkResult?.isSuccess == true) {
		AlertErroDialog(dialogText = loginState.value.networkResult?.message ?: "") {
			signupButtonClick.invoke()
		}
	} else {
		loginState.value.networkResult?.message?.let {

			if (it.isNotEmpty() && errorOccured) {
				Log.e("Sign UP ", "*********** ${it}")

				AlertErroDialog(dialogText = it) {
					errorOccured = false
				}
			}
		}
	}
}


