package com.example.mrrecipe.screens.addRecipe

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mrrecipe.RecipeData
import com.example.mrrecipe.Utils
import com.example.mrrecipe.screens.loginSignup.LoginViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddRecpieViewModel : ViewModel() {

	private val _addRecipeUIState =
		MutableStateFlow(
			RecipeData(
				ingrediants = emptyList(), dislikedUsers = listOf("11"),
				likedUsers = listOf("11")
			)
		)
	internal val addRecipeUIState = _addRecipeUIState.asStateFlow()

	private val _pickerState = MutableStateFlow(ImagePickerState(false))
	internal val pickerState = _pickerState.asStateFlow()

	private val _forValidationUIState = MutableStateFlow(FormValidationData())
	internal val forValidationUIState = _forValidationUIState.asStateFlow()

	private var firebaseFirestore: FirebaseFirestore

	val database: FirebaseDatabase

	val firebaseStorage = Firebase.storage("gs://authtest-2b3db.appspot.com")
	var imagesRef: StorageReference? = firebaseStorage.reference.child("images")


	private var currentUser: String?

	init {
		firebaseFirestore = FirebaseFirestore.getInstance()
		currentUser = Firebase.auth.currentUser?.uid
		database = FirebaseDatabase.getInstance("https://authtest-2b3db.firebaseio.com")


	}

	fun updatePickerState() {
		_pickerState.update { _pickerState.value.copy(open = _pickerState.value.open.not()) }
	}

	fun updateTitle(input: String) {
		_addRecipeUIState.update { _addRecipeUIState.value.copy(title = input) }
	}

	fun updateDescription(input: String) {
		_addRecipeUIState.update { _addRecipeUIState.value.copy(description = input) }
	}

	fun updateIngrediantList(input: String) {
		val ingrediantList = _addRecipeUIState.value.ingrediants.toMutableList()
		ingrediantList.add(input)
		_addRecipeUIState.update { _addRecipeUIState.value.copy(ingrediants = ingrediantList) }
	}

	fun removeFromIngrediantList(input: String) {
		val ingrediantList = _addRecipeUIState.value.ingrediants.toMutableList()
		ingrediantList.remove(input)
		_addRecipeUIState.update { _addRecipeUIState.value.copy(ingrediants = ingrediantList) }
	}

	fun updateFormState(isValidated: Boolean, errorMessage: String = "") {
		_forValidationUIState.value = FormValidationData(isValidated, errorMessage)
	}


	fun addRecipe() {

		Log.e("Current user", "is $currentUser")

		_forValidationUIState.update { _forValidationUIState.value.copy(isLoading = true) }

		currentUser?.let { userName ->


//			database.getReference(Utils.DB_NAME_RECIPE).child(userName)
			database.getReference(Utils.DB_NAME_RECIPE)
				.child(System.currentTimeMillis().toString())
				.setValue(addRecipeUIState.value).addOnFailureListener { exception ->
					_forValidationUIState.update {
						_forValidationUIState.value.copy(
							isLoading = false,
							isFailure = true,
							message = "${exception.message}"
						)
					}
				}.addOnSuccessListener {
					_forValidationUIState.update {
						_forValidationUIState.value.copy(
							isLoading = false,
							isSuccess = true,
							message = "Recipe added successfully"
						)
					}
				}
		}

	}

	fun uploadImage(uri: Uri?) {
		uri?.let {
			val ref = imagesRef?.child(System.currentTimeMillis().toString())

			val uploadTask = ref?.putFile(it)

			val urlTask = uploadTask?.continueWithTask { task ->
				if (!task.isSuccessful) {
					task.exception?.let {
						throw it
					}
				}
				ref.downloadUrl
			}?.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					val downloadUri = task.result//Image url

					_addRecipeUIState.update { _addRecipeUIState.value.copy(imgUrl = downloadUri.toString()) }

				} else {

				}
			}


		}
	}
}