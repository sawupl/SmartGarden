package com.example.smartgarden

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartgarden.model.Plant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PlantViewModel(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : ViewModel() {

    val id = auth.currentUser?.uid.toString()
    val plantLiveData = MutableLiveData<List<Plant>>()

    init {
        getPlants()
    }

    private fun getPlants(){
        val streetsList = mutableListOf<Plant>()
        viewModelScope.launch(Dispatchers.IO) {
            val streets = db.collection("plant").get().await()
            streets.documents.forEach {
                println(it.data?.get("name").toString())
                val name = it.data?.get("name").toString()
                val soilType = it.data?.get("soilType").toString()
                val watering = it.data?.get("watering").toString().toLong()
                val picture = it.data?.get("picture").toString()
                streetsList.add(Plant(name,watering,soilType,picture))
            }
            plantLiveData.postValue(streetsList)
        }
    }
}