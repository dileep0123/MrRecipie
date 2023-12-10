package com.example.mrrecipe.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrrecipe.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeUI(addRecipieClick: () -> Unit) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Add Recipe",
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
		val ingrediantName = arrayListOf<String>("Salt", "Pepper", "Onion", "Ginger", "Garlic")
		LazyColumn(
			Modifier
				.padding(paddingValues)
				.padding(15.dp)
		) {

			item {

				AddImage()
				AddTitleAndDescription("Title")
				AddTitleAndDescription("Description")
				AddIngrediants("Add Ingrediants")
				LazyRow(Modifier.padding(vertical = 8.dp)) {
					itemsIndexed(ingrediantName) { index, item ->
						AddChips(item)
					}
				}
				AddRecipieButton(addRecipieClick)
			}
		}
	}

}

@Composable
fun AddImage() {
	OutlinedCard(
		colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(
			1.dp, color = Color.Black
		), modifier = Modifier
			.height(150.dp)
			.fillMaxHeight()
	) {

		Box(modifier = Modifier.fillMaxWidth()) {
			Image(
				painterResource(id = R.drawable.ic_add_image),
				contentDescription = "",
				Modifier
					.fillMaxWidth()
					.fillMaxHeight()
			)
			Icon(
				Icons.Filled.AddCircle,
				contentDescription = "Check mark",
				Modifier
					.align(Alignment.BottomEnd)
					.padding(8.dp)
					.size(30.dp),
				tint = MaterialTheme.colorScheme.primary
			)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTitleAndDescription(title: String) {
	var text by rememberSaveable { mutableStateOf("") }

	Row(
		Modifier
			.fillMaxWidth()
			.padding(vertical = 20.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.Top
	) {
		Text(
			text = title, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 10.sp
		)

		OutlinedTextField(
			value = text,
			onValueChange = { text = it }
		)
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngrediants(title: String) {
	var text by rememberSaveable { mutableStateOf("") }

	Row(
		Modifier
			.fillMaxWidth()
			.padding(vertical = 10.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically,
	) {
		Text(
			text = title, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 10.sp
		)

		OutlinedTextField(
			value = text,
			onValueChange = { text = it },
			modifier = Modifier
				.weight(1f)
				.padding(horizontal = 5.dp),
			maxLines = 1,
			singleLine = true
		)

		ElevatedButton(
			onClick = { }, colors = ButtonDefaults.elevatedButtonColors(
				containerColor = MaterialTheme.colorScheme.primary
			)
		) {
			Text(text = "Add", color = Color.White, fontWeight = FontWeight.Bold)
		}
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChips(item: String) {
	AssistChip(onClick = { }, label = { Text(text = item) }, Modifier.padding(5.dp), trailingIcon = {
		Icon(
			Icons.Filled.Close,
			contentDescription = "Localized description",
			Modifier.size(AssistChipDefaults.IconSize)
		)
	})
}

@Composable
fun AddRecipieButton(addRecipieClick: () -> Unit) {
	ElevatedButton(
		onClick = { addRecipieClick.invoke() }, colors = ButtonDefaults.elevatedButtonColors(
			containerColor = MaterialTheme.colorScheme.primary
		),
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 15.dp)
			.defaultMinSize(minHeight = 50.dp)
	) {
		Text(text = "Add Recpie", color = Color.White, fontWeight = FontWeight.Bold)
	}
}


