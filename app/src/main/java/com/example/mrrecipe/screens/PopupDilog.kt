package com.example.mrrecipe.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.lang.Error

@Composable
fun AlertErroDialog(
	dialogText: String,
	onDismissRequest: () -> Unit,

	) {
	MaterialTheme {


		AlertDialog(
			icon = {
				Icon(Icons.Default.Info, contentDescription = "Example Icon", tint = Color.Red)
			},

			text = {
				Text(text = dialogText, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
			},
			onDismissRequest = {

			},
			confirmButton = {

			},
			dismissButton = {
				Button(

					onClick = {
						onDismissRequest.invoke()
					}) {
					Text("OK")
				}
			},
			shape = MaterialTheme.shapes.small,

			)
	}
}