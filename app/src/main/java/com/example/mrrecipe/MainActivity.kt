package com.example.mrrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mrrecipe.navigation.Screens
import com.example.mrrecipe.navigation.StartAppNavigation
import com.example.mrrecipe.ui.theme.MrRecipeTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MrRecipeTheme {
				// A surface container using the 'background' color from the theme
//				LoginScreen()
				StartAppNavigation(Screens.LOGIN)
			}
		}
	}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	Text(
		text = "Hello $name!",
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	MrRecipeTheme {
		Greeting("Android")
	}
}