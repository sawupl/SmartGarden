package com.example.smartgarden.garden

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartgarden.model.Plant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class GardenViewModel(private val db: FirebaseFirestore, private val auth: FirebaseAuth): ViewModel() {
    val id = auth.currentUser?.uid.toString()
    val plantListLivaData = MutableLiveData<MutableList<Plant>>()
    val nameLivaData = MutableLiveData<String>()

    fun getGarden(gardenId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val garden = db.collection("users")
                .document(id)
                .collection("garden")
                .document(gardenId)
            val name = garden.get().await().data?.get("name").toString()
            nameLivaData.postValue(name)


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
                    plantsList.add(Plant(name,watering,soilType,picture,plantId))
                }
            }
            plantListLivaData.postValue(plantsList)
        }
    }
}