package com.example.smartgarden

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartgarden.model.Garden
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
    val plantListLivaData = MutableLiveData<MutableList<Plant>>()
    val gardenLivaData = MutableLiveData<Garden>()

    init {
        getPlants()
    }

    private fun getPlants(){
        val plantsStringList = mutableListOf<String>()
        viewModelScope.launch(Dispatchers.IO) {
            val plants = db.collection("plant").get().await()
            plants.documents.forEach {
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


    fun getGarden(gardenId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // get id list of gardens plants
            val plantIdList = mutableListOf<String>()
            val plantsInGardenId = db.collection("users")
                .document(id)
                .collection("garden")
                .document(gardenId)
                .collection("plants").get().await()
            plantsInGardenId.documents.forEach {
                val plantId = it.id
                plantIdList.add(plantId)
            }

            val userRef = db.collection("users").document(id).collection("garden").document(gardenId).get().await()
            val gardName = userRef.data?.get("name").toString()
            val cityName = userRef.data?.get("city").toString()
            gardenLivaData.postValue(Garden(gardenId,gardName,cityName))
            // list of plants
            val plantsList = mutableListOf<Plant>()
            val plantsRef = db.collection("plant").get().await()
            plantsRef.documents.forEach { plantItem ->
                val plantId = plantItem.id
                val name = plantItem.data?.get("name").toString()
                val soilType = plantItem.data?.get("soilType").toString()
                val watering = plantItem.data?.get("watering").toString().toLong()
                val picture = plantItem.data?.get("picture").toString()
                if (plantId in plantIdList) {
                    plantsList.add(Plant(name, watering, soilType, picture, plantId))
                }
            }
            plantListLivaData.postValue(plantsList)
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

        fun saveGarden(city: String, name: String, plants: MutableList<Plant>, gardenId:String?) {

            if (!gardenId.isNullOrEmpty()){
                val garden = hashMapOf(
                    "name" to name,
                    "city" to city
                )
                db.collection("users")
                    .document(id)
                    .collection("garden")
                    .document(gardenId).set(garden)
                    db.collection("users")
                        .document(id)
                        .collection("garden")
                        .document(gardenId)
                        .collection("plants").get().addOnSuccessListener{
                            it.forEach { plant ->
                                plant.reference.delete()
                            }
                        }.addOnCompleteListener {
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
            else{
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
}