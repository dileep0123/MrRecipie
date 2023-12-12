package com.example.mrrecipe.screens.dashboard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mrrecipe.Loader
import com.example.mrrecipe.R
import com.example.mrrecipe.RecipeData
import com.example.mrrecipe.screens.addRecipe.AddChips
import com.example.mrrecipe.screens.addRecipe.AddRecpieViewModel
import com.example.mrrecipe.screens.addRecipe.FormValidationData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
	dashboardViewModel: DashboardViewModel,
	uiState: State<DashboardViewModel.DashboardUIState>,
	fabClick: () -> Unit,
	logoutUserClick: () -> Unit,
	heartButtonClick: (String, RecipeData) -> Unit,
	deleteButtonClick: (String, RecipeData) -> Unit
) {
	LaunchedEffect(true) {
		//Calling inside LaunchedEffect to avoid unnecessary additional calls.
		dashboardViewModel.fetchData()
	}
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
				navigationIcon = {
					Button(onClick = { logoutUserClick.invoke() }) {
						Text(
							text = "Logout",
							fontSize = 15.sp,
							fontWeight = FontWeight.Medium,
							textAlign = TextAlign.Start,
							color = Color.White
						)
					}
				}

			)


		},
		floatingActionButton = {
			FloatingActionButton(onClick = { fabClick.invoke() }) {
				Icon(Icons.Default.Add, contentDescription = "")
			}
		},
	) { paddingValues ->
		val itemList = arrayListOf<String>("1", "2", "3", "4")

		LazyColumn(
			Modifier
				.padding(paddingValues)
				.padding(horizontal = 15.dp)
		) {

			uiState.value.recipeList?.let {
				if (it.isEmpty().not()) {

					it.forEach { (key, value) ->
						Log.e("In items ", "${key}  -- $value")
						item {
							if (value.dislikedUsers.contains(dashboardViewModel.getCurrentuser()).not()) {
								ListItemUI(value, key, dashboardViewModel, heartButtonClick, deleteButtonClick)
							}
						}
					}
				}
			}
		}

		if (uiState.value.isLoading) {
			Loader()
		}
	}

}

@Composable
fun ListItemUI(
	recipeData: RecipeData,
	key: String,
	dashboardViewModel: DashboardViewModel,
	heartButtonClick: (String, RecipeData) -> Unit,
	deleteButtonClick: (String, RecipeData) -> Unit
) {

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
				AsyncImage(
					recipeData.imgUrl,
					contentDescription = "",
					modifier = Modifier
						.size(85.dp)
						.clip(RoundedCornerShape(8.dp)),
					error = painterResource(id = R.drawable.ic_pizza),
					contentScale = ContentScale.FillBounds
				)
				Column(
					Modifier
						.fillMaxHeight()
						.weight(1f)
				) {
					Text(
						text = recipeData.title,
						maxLines = 1,
						fontWeight = FontWeight.Bold,
						color = Color.Black,
						overflow = TextOverflow.Ellipsis,
						modifier = Modifier.padding(5.dp)
					)
					Text(
						text = recipeData.description,
						fontWeight = FontWeight.Bold,
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


					IconButton(onClick = {
						heartButtonClick.invoke(
							key,
							recipeData.copy(favourite = recipeData.favourite.not())
						)
					}) {
						Icon(
							Icons.Default.Favorite, contentDescription = "", tint =
							if (recipeData.likedUsers.contains(
									dashboardViewModel.getCurrentuser().toString()
								)
							) Color.Red else Color.Gray
						)
					}

					IconButton(onClick = {
						deleteButtonClick.invoke(
							key,
							recipeData
						)
					}) {
						Icon(
							Icons.Default.Delete, contentDescription = "", tint =
							if (recipeData.favourite) Color.Red else Color.Red
						)
					}
				}

			}

			LazyRow(Modifier.padding(vertical = 2.dp)) {
				itemsIndexed(recipeData.ingrediants) { index, item ->
					AddChipsForUI(item)
				}
			}
		}

	}


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChipsForUI(
	item: String
) {
	AssistChip(onClick = { }, label = { Text(text = item) }, Modifier.padding(5.dp))

}