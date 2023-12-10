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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrrecipe.R
import com.example.mrrecipe.screens.AlertErroDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(signupButtonClick: () -> Unit, loginViewModel: LoginViewModel) {
	var username by rememberSaveable { mutableStateOf("") }
	Log.e("item "," ** $username")
	var password by rememberSaveable { mutableStateOf("") }
	var confirmPassword by rememberSaveable { mutableStateOf("") }

	var isFormValidate by rememberSaveable { mutableStateOf(false) }
	var errorMessage by rememberSaveable { mutableStateOf("") }
	val openAlertDialog = rememberSaveable { mutableStateOf(false) }

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

//				navigationIcon = {
//					IconButton(onClick = { onBackClick.invoke() }) {
//						Icon(
//							painter = painterResource(id = R.drawable.ic_arrow_left_24dp),
//							contentDescription = stringResource(id = R.string.label_back),
//							tint = colorResource(id = R.color.icon_on_fab)
//						)
//					}
//				}
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
						value = username,
						onValueChange = {
//							loginViewModel.updateUsername(it)
										username=it
						},
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp),
						singleLine = true,
						maxLines = 1

					)
					OutlinedTextField(
						label = { Text(text = "Enter Password") },
						value = password,
						onValueChange = {
//							loginViewModel.updatePassword(it)
										password=it
						},
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp),
						singleLine = true,
						maxLines = 1
					)

					OutlinedTextField(
						label = { Text(text = "Confirm Password") },
						value = confirmPassword,
						onValueChange = {
//							loginViewModel.updateConfirmPassword(it)
										confirmPassword=it
						},
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp),
						singleLine = true,
						maxLines = 1
					)

					ElevatedButton(
						onClick = {


							if (username.isEmpty()) {
								errorMessage = "Please enter email ."
								isFormValidate = true
							} else if (password.isEmpty()) {
								errorMessage = "Please enter password ."
								isFormValidate = true
							} else if (confirmPassword.isEmpty()) {
								errorMessage = "Please enter confirm password ."
								isFormValidate = true
							} else if (confirmPassword
									.equals(password, ignoreCase = false).not()
							) {
								errorMessage = "Password and Confirm passwrod did not match ."
								isFormValidate = true
							} else {
								System.out.println("Ready to create account")
								signupButtonClick.invoke()

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
}

fun ValidateSignup() {

}

