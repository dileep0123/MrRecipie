package com.example.mrrecipe.screens.addRecipe


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.mrrecipe.Loader
import com.example.mrrecipe.R
import com.example.mrrecipe.RecipeData
import com.example.mrrecipe.screens.AlertErroDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeUI(
	addRecpieViewModel: AddRecpieViewModel,
	uiState: State<RecipeData>,
	uiFormState: State<FormValidationData>,
	imagePickerState: State<ImagePickerState>,
	addRecipieClick: () -> Unit
) {

	var isFormValidate by rememberSaveable { mutableStateOf(false) }
	var errorMessage by rememberSaveable { mutableStateOf("") }

	var openImagePicker by rememberSaveable { mutableStateOf(false) }

	var imageUri by remember { mutableStateOf<Uri?>(null) }
	val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->

		imageUri = uri

		addRecpieViewModel.uploadImage(imageUri)
	}

	val item = {
		launcher.launch("image/*")
	}


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

				AddImage(uiState, addRecpieViewModel, uiFormState, imagePickerState, imageUri, item)
				AddTitle("Title", uiState, addRecpieViewModel, uiFormState)
				AddDescription("Description", uiState, addRecpieViewModel, uiFormState)
				AddIngrediants("Add Ingrediants", uiState, addRecpieViewModel, uiFormState)
				LazyRow(Modifier.padding(vertical = 8.dp)) {
					itemsIndexed(uiState.value.ingrediants) { index, item ->
						AddChips(item, uiState, addRecpieViewModel, uiFormState)
					}
				}
				AddRecipieButton(addRecipieClick, uiState, addRecpieViewModel, uiFormState)
			}
		}

		if (uiFormState.value.isValidated || uiFormState.value.isFailure) {
			AlertErroDialog(dialogText = uiFormState.value.message) {
				addRecpieViewModel.updateFormState(false)
			}
		} else if (uiFormState.value.isSuccess) {
			AlertErroDialog(dialogText = uiFormState.value.message) {
				addRecipieClick.invoke()
			}
		}

		if (uiFormState.value.isLoading) {
			Loader()
		}

	}

	if (imagePickerState.value.open) {
		//PhotoSelectorView(1)

	}


}

@Composable
fun AddImage(
	uiState: State<RecipeData>,
	addRecpieViewModel: AddRecpieViewModel,
	uiFormState: State<FormValidationData>,
	imagePickerState: State<ImagePickerState>,
	uri: Uri?,
	addRecipieClick: () -> Unit
) {
	OutlinedCard(
		colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(
			1.dp, color = Color.Black
		), modifier = Modifier
			.height(150.dp)
			.fillMaxHeight()
	) {

		Box(modifier = Modifier.fillMaxWidth()) {
			Image(
				painter = rememberAsyncImagePainter(model = uri),
				contentDescription = "",
				Modifier
					.fillMaxWidth()
					.fillMaxHeight()
					.clip(RoundedCornerShape(8.dp)),
				contentScale = ContentScale.FillBounds,

				)
			IconButton(onClick = {
//				addRecpieViewModel.updatePickerState()
				addRecipieClick.invoke()
			}
			) {

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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTitle(
	title: String,
	uiState: State<RecipeData>,
	addRecpieViewModel: AddRecpieViewModel,
	uiFormState: State<FormValidationData>
) {
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
			value = uiState.value.title,
			onValueChange = { addRecpieViewModel.updateTitle(it) }
		)
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDescription(
	title: String,
	uiState: State<RecipeData>,
	addRecpieViewModel: AddRecpieViewModel,
	uiFormState: State<FormValidationData>
) {
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
			value = uiState.value.description,
			onValueChange = { addRecpieViewModel.updateDescription(it) }
		)
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddIngrediants(
	title: String,
	uiState: State<RecipeData>,
	addRecpieViewModel: AddRecpieViewModel,
	uiFormState: State<FormValidationData>
) {
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
			onClick = {
				if (text.isEmpty()) {
					val errorMessage = "Enter text to add ingrediants."
					addRecpieViewModel.updateFormState(true, errorMessage)
				} else {
					addRecpieViewModel.updateIngrediantList(text)
					text = ""
				}
			}, colors = ButtonDefaults.elevatedButtonColors(
				containerColor = MaterialTheme.colorScheme.primary
			)
		) {
			Text(text = "Add", color = Color.White, fontWeight = FontWeight.Bold)
		}
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChips(
	item: String,
	uiState: State<RecipeData>,
	addRecpieViewModel: AddRecpieViewModel,
	uiFormState: State<FormValidationData>
) {
	AssistChip(onClick = { }, label = { Text(text = item) }, Modifier.padding(5.dp), trailingIcon = {
		Icon(
			Icons.Filled.Close,
			contentDescription = "Localized description",
			Modifier.size(AssistChipDefaults.IconSize)
		)
	})
}

@Composable
fun AddRecipieButton(
	addRecipieClick: () -> Unit,
	uiState: State<RecipeData>,
	addRecpieViewModel: AddRecpieViewModel,
	uiFormState: State<FormValidationData>
) {
	ElevatedButton(
		onClick = {

			if (uiState.value.title.isEmpty()) {
				val errorMessage = "Enter title."
				addRecpieViewModel.updateFormState(true, errorMessage)
			} else if (uiState.value.description.isEmpty()) {
				val errorMessage = "Enter Description."
				addRecpieViewModel.updateFormState(true, errorMessage)
			} else if (uiState.value.ingrediants.isEmpty()) {
				val errorMessage = "Enter at least on ingrediant."
				addRecpieViewModel.updateFormState(true, errorMessage)
			} else {


				addRecpieViewModel.addRecipe()
			}
		}, colors = ButtonDefaults.elevatedButtonColors(
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

@Composable
fun PhotoSelectorView(maxSelectionCount: Int = 1) {
	var selectedImages by remember {
		mutableStateOf<List<Uri?>>(emptyList())
	}

	val buttonText = if (maxSelectionCount > 1) {
		"Select up to $maxSelectionCount photos"
	} else {
		"Select a photo"
	}

	val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.PickVisualMedia(),
		onResult = { uri -> selectedImages = listOf(uri) }
	)

	// I will start this off by saying that I am still learning Android development:
	// We are tricking the multiple photos picker here which is probably not the best way,
	// if you know of a better way to implement this feature drop a comment and let me know
	// how to improve this design
	val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.PickMultipleVisualMedia(
			maxItems = if (maxSelectionCount > 1) {
				maxSelectionCount
			} else {
				2
			}
		),
		onResult = { uris -> selectedImages = uris }
	)

//	fun launchPhotoPicker() {
//		if (maxSelectionCount > 1) {
//			multiplePhotoPickerLauncher.launch(
//				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//			)
//		} else {
//			singlePhotoPickerLauncher.launch(
//				PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
//			)
//		}
//	}

	singlePhotoPickerLauncher.launch(
		PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
	)

	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Button(onClick = {
//			launchPhotoPicker()
		}) {
			Text(buttonText)
		}

		ImageLayoutView(selectedImages = selectedImages)
	}
}

@Composable
fun ImageLayoutView(selectedImages: List<Uri?>) {
	LazyRow {
		items(selectedImages) { uri ->
			AsyncImage(
				model = uri,
				contentDescription = null,
				modifier = Modifier.fillMaxWidth(),
				contentScale = ContentScale.Fit
			)
		}
	}
}




