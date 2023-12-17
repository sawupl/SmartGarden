package com.example.smartgarden

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartgarden.model.PlantInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PlantInfoViewModel(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : ViewModel() {

    val plantInfoLiveData = MutableLiveData<PlantInfo>()

    fun getPlantInfo(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            val plant = db.collection("plant").document(id).get().await()
            val name = plant.data?.get("name").toString()
            val watering = plant.data?.get("watering").toString().toLong()
            val soilType = plant.data?.get("soilType").toString()
            val bloomTime = plant.data?.get("bloomTime").toString()
            val info = plant.data?.get("info").toString()
            val fertilizer = plant.data?.get("fertilizer").toString()
            val size = plant.data?.get("size").toString()
            val soilInfo = plant.data?.get("soilInfo").toString()
            val soilPh = plant.data?.get("soilPh").toString()
            val sunExposure = plant.data?.get("sunExposure").toString()
            val picture = plant.data?.get("picture").toString()
            val sunExposureInfo = plant.data?.get("sunExposureInfo").toString()
            val tempAndHumidity = plant.data?.get("tempAndHumidity").toString()
            val toxicity = plant.data?.get("toxicity").toString()
            val waterInfo = plant.data?.get("waterInfo").toString()
            plantInfoLiveData.postValue(PlantInfo(name,watering, soilType, bloomTime, info, fertilizer, size, soilInfo, soilPh, sunExposure, picture, sunExposureInfo, tempAndHumidity, toxicity, waterInfo))
        }
    }
}