package com.example.mrrecipe.screens.loginSignup

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LoginScreen(loginButtonCLick: () -> Unit, signupButtonCLick: () -> Unit, loginViewModel: LoginViewModel) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Login",
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

		var email by rememberSaveable { mutableStateOf("") }
		var password by rememberSaveable { mutableStateOf("") }

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
						value = email,
						onValueChange = {
							email = it
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
							password = it
						},
						modifier = Modifier
							.fillMaxWidth()
							.padding(8.dp),
						singleLine = true,
						maxLines = 1
					)

					ElevatedButton(
						onClick = { loginButtonCLick.invoke() }, colors = ButtonDefaults.elevatedButtonColors(
							containerColor = MaterialTheme.colorScheme.primary
						),
						modifier = Modifier.padding(top = 20.dp)
					) {
						Text(text = "SIGN IN", color = Color.White, fontWeight = FontWeight.Bold)
					}

				}
			}


			Row(
				horizontalArrangement = Arrangement.SpaceEvenly,
				modifier = Modifier
					.padding(top = 50.dp)
					.fillMaxWidth(),
				verticalAlignment = Alignment.CenterVertically
			) {

				Divider(Modifier.width(100.dp))
				Text(text = "OR", fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 20.sp)
				Divider(Modifier.width(100.dp))
			}

			ElevatedButton(
				onClick = { signupButtonCLick.invoke() }, colors = ButtonDefaults.elevatedButtonColors(
					containerColor = MaterialTheme.colorScheme.primary
				),
				modifier = Modifier.padding(top = 20.dp)
			) {
				Text(text = "SIGN UP", color = Color.White, fontWeight = FontWeight.Bold)
			}


		}


	}
}