package com.example.mrrecipe.screens.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mrrecipe.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(fabClick: () -> Unit) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Dashboard",
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
		},
		floatingActionButton = {
			FloatingActionButton(onClick = { fabClick.invoke() }) {
				Icon(Icons.Default.Add, contentDescription = "")
			}
		}
	) { paddingValues ->
		val itemList = arrayListOf<String>("1", "2", "3", "4")
		LazyColumn(
			Modifier
				.padding(paddingValues)
				.padding(horizontal = 15.dp)
		) {

			itemsIndexed(itemList) { index, item ->
				ListItemUI()
			}
		}
	}

}

@Composable
fun ListItemUI() {

	ElevatedCard(
		elevation = CardDefaults.cardElevation(
			defaultElevation = 6.dp
		),
		modifier = Modifier
			.fillMaxWidth()
			.padding(top = 20.dp),

		) {
		Column(
			Modifier
				.fillMaxWidth()
				.padding(10.dp)
		) {
			Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
				Image(
					painter = painterResource(id = R.drawable.ic_pizza),
					contentDescription = "",
					modifier = Modifier
						.size(85.dp)
				)
				Column(
					Modifier
						.fillMaxHeight()
						.weight(1f)
				) {
					Text(
						text = "Recipie Title",
						maxLines = 1,
						fontWeight = FontWeight.Bold,
						color = Color.Black,
						overflow = TextOverflow.Ellipsis,
						modifier = Modifier.padding(5.dp)
					)
					Text(
						text = "Description for reciepie ajk  sdsd sder wefrweref dwedwe dwedwe wedwedwe sd sd sd sd sd kjnd dsjn csd sjkdn dc",
						fontWeight = FontWeight.Thin,
						color = Color.Black,
						modifier = Modifier.padding(5.dp),
						fontSize = 12.sp,
						maxLines = 3,
						overflow = TextOverflow.Ellipsis
					)
				}

				Column(
					Modifier
						.fillMaxHeight(), verticalArrangement = Arrangement.Bottom
				) {

					Icon(Icons.Default.Favorite, contentDescription = "", tint = Color.Red)
				}

			}


		}

	}
}