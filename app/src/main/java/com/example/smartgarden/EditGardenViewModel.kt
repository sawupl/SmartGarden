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
import java.util.UUID

class EditGardenViewModel(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : ViewModel() {

    val id = auth.currentUser?.uid.toString()
    val plantsStringLiveData = MutableLiveData<List<String>>()
    private val plantList = mutableListOf<Plant>()

    init {
        getPlants()
    }

    private fun getPlants(){
        val plantsStringList = mutableListOf<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val plants = db.collection("plant").get().await()
            plants.documents.forEach {
                println(it.data?.get("name").toString())
                val name = it.data?.get("name").toString()
                val id = it.id
                val soilType = it.data?.get("soilType").toString()
                val watering = it.data?.get("watering").toString().toLong()
                val picture = it.data?.get("picture").toString()
                plantsStringList.add(name)
                plantList.add(Plant(name,watering,soilType,picture,id))
            }
            plantsStringLiveData.postValue(plantsStringList)
        }
    }


    fun addPlant(name: String): Plant? {
        var plant: Plant? = null
        for (i in plantList) {
            if (i.name == name) {
                plant = i
                break
            }
        }
        return plant
    }

    fun saveGarden(city: String, name: String, plants: MutableList<Plant>) {
            val garden = hashMapOf(
                "name" to name,
                "city" to city
            )
        val gardenId = UUID.randomUUID().toString()
            db.collection("users")
                .document(id)
                .collection("garden")
                .document(gardenId)
                .set(garden)
        for (i in plants) {
            db.collection("users")
                .document(id)
                .collection("garden")
                .document(gardenId)
                .collection("plants")
                .document(i.id)
                .set(mapOf("added" to true))
        }
    }
}