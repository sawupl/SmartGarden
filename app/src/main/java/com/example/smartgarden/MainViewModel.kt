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

class MainViewModel(private val db: FirebaseFirestore, private val auth: FirebaseAuth) : ViewModel() {

    val id = auth.currentUser?.uid.toString()
    val gardenLiveData = MutableLiveData<MutableList<Garden>>()

    fun getGarden(){
        val gardenList = mutableListOf<Garden>()
        viewModelScope.launch(Dispatchers.IO) {
            val gardens = db.collection("users").document(id).collection("garden").get().await()
            gardens.documents.forEach {
                val name = it.data?.get("name").toString()
                val id = it.id
                val city = it.data?.get("city").toString()
                gardenList.add(Garden(name = name,id = id,city=city))
            }
            gardenLiveData.postValue(gardenList)
        }
    }


    fun deleteGarden(gardenId: String){
        viewModelScope.launch(Dispatchers.IO) {
            db.collection("users")
                .document(id)
                .collection("garden")
                .document(gardenId)
                .delete().await()
        }
    }
}