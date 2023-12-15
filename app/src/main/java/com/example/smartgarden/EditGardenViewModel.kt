package com.example.smartgarden

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EditGardenViewModel(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : ViewModel() {

    val id = auth.currentUser?.uid.toString()
    val plantLiveData = MutableLiveData<List<String>>()

    init {
        getPlants()
    }

    private fun getPlants(){
        val streetsList = mutableListOf<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val streets = db.collection("plant").get().await()
            streets.documents.forEach {
                println(it.data?.get("name").toString())
                streetsList.add(it.data?.get("name").toString())
            }
            plantLiveData.postValue(streetsList)
        }
    }
}