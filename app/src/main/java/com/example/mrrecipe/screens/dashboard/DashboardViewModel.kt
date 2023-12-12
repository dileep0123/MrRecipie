package com.example.mrrecipe.screens.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mrrecipe.NetworkResult
import com.example.mrrecipe.RecipeData
import com.example.mrrecipe.Utils
import com.example.mrrecipe.screens.loginSignup.LoginViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.json.JSONObject


class DashboardViewModel : ViewModel() {


	private var firebaseFirestore: FirebaseFirestore

	private var currentUser: String?

	private var TAG = DashboardViewModel::class.java.name

	val database: FirebaseDatabase


	private val _dashBoardIState = MutableStateFlow(DashboardUIState())
	internal val dashBoardIState = _dashBoardIState.asStateFlow()

	init {
		firebaseFirestore = FirebaseFirestore.getInstance()
//		currentUser = Firebase.auth.currentUser?.uid
		currentUser = Firebase.auth.currentUser?.email
		database = FirebaseDatabase.getInstance("https://authtest-2b3db.firebaseio.com")
	}

	fun fetchData() {
		currentUser?.let { currentUser ->
			_dashBoardIState.update {
				_dashBoardIState.value.copy(
					recipeList = null, isLoading = true
				)
			}
//			database.getReference(Utils.DB_NAME_RECIPE).child(currentUser).get()
			database.getReference(Utils.DB_NAME_RECIPE).get()
				.addOnCompleteListener(OnCompleteListener<DataSnapshot> { task ->
					if (!task.isSuccessful) {
						Log.e("firebase", "Error getting data", task.exception)
						_dashBoardIState.update {
							_dashBoardIState.value.copy(
								recipeList = null, isLoading = false
							)
						}

					} else {

						val mapList = HashMap<String, RecipeData>()

						task.result?.value?.let {

							val data = task.result.value as HashMap<String, *>

							val jsonObject = JSONObject(data)

							data.forEach { key, values ->

								val rawJson = jsonObject.get(key).toString()

								Log.e("User key ", "is - $key")
								Log.e("User key 2 ", "is - $rawJson")

								val userObject = Gson().fromJson(rawJson.toString(), RecipeData::class.java)

								mapList.put(key, userObject)

								Log.e("Keys ", "$key --- $values $mapList")

							}

							_dashBoardIState.update {
								_dashBoardIState.value.copy(
									recipeList = mapList, isLoading = false
								)
							}
						} ?: _dashBoardIState.update {
							_dashBoardIState.value.copy(
								isLoading = false
							)
						}
					}
				})


		}
	}

	fun logoutUser() {
		Firebase.auth.signOut()
	}

	fun getCurrentuser() = currentUser

	fun updateUserData(id: String, data: RecipeData) {

		_dashBoardIState.update {
			_dashBoardIState.value.copy(
				isLoading = true,
			)
		}

		currentUser?.let {

			val updateRecord = data
			val list = updateRecord.likedUsers.toMutableList()
			if (updateRecord.favourite) {
				list.add(currentUser.toString())
			} else {
				list.remove(currentUser.toString())
			}
			val sendingRecord = data.copy(likedUsers = list)


			database.getReference(Utils.DB_NAME_RECIPE)
				.child(id)
				.setValue(sendingRecord).addOnFailureListener { exception ->
					_dashBoardIState.update {
						_dashBoardIState.value.copy(
							isLoading = false
						)
					}
				}.addOnSuccessListener {
					_dashBoardIState.update {
						_dashBoardIState.value.copy(
							isLoading = false
						)
					}
				}
		}
	}

	fun dislikeUserData(id: String, data: RecipeData) {

		_dashBoardIState.update {
			_dashBoardIState.value.copy(
				isLoading = true,
			)
		}

		currentUser?.let {

			val updateRecord = data
			val list = updateRecord.dislikedUsers.toMutableList()
			list.add(currentUser.toString())
			val sendingRecord = data.copy(dislikedUsers = list)


			database.getReference(Utils.DB_NAME_RECIPE)
				.child(id)
				.setValue(sendingRecord).addOnFailureListener { exception ->
					_dashBoardIState.update {
						_dashBoardIState.value.copy(
							isLoading = false
						)
					}
				}.addOnSuccessListener {
					_dashBoardIState.update {
						_dashBoardIState.value.copy(
							isLoading = false
						)
					}
				}
		}
	}

	fun updateForSingle(currentUser: String) {
		database.getReference(Utils.DB_NAME_RECIPE).child(currentUser)
			.addListenerForSingleValueEvent(object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					val mapList = HashMap<String, RecipeData>()

					snapshot.value?.let { task ->


						val data = task as HashMap<String, *>

						val jsonObject = JSONObject(data)

						data.forEach { key, values ->

							val rawJson = jsonObject.get(key).toString()

							Log.e("User key ", "is - $key")
							Log.e("User key 2 ", "is - $rawJson")

							val userObject = Gson().fromJson(rawJson.toString(), RecipeData::class.java)

							mapList.put(key, userObject)

							Log.e("Keys ", "$key --- $values $mapList")

						}

						_dashBoardIState.update {
							_dashBoardIState.value.copy(
								recipeList = mapList, isLoading = false
							)
						}
					} ?: _dashBoardIState.update {
						_dashBoardIState.value.copy(
							isLoading = false
						)
					}
				}

				override fun onCancelled(error: DatabaseError) {
				}

			})
	}

	data class DashboardUIState(
		var recipeList: HashMap<String, RecipeData>? = null,
		var isLoading: Boolean = false
	)

}