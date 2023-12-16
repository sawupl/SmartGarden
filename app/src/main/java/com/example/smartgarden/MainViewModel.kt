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

    init {
        getGarden()
    }

    private fun getGarden(){
        val gardenList = mutableListOf<Garden>()
        viewModelScope.launch(Dispatchers.IO) {
            val gardens = db.collection("users").document(id).collection("garden").get().await()
            gardens.documents.forEach {
                val name = it.data?.get("name").toString()
                val id = it.id
                gardenList.add(Garden(name = name,id = id))
            }
            gardenLiveData.postValue(gardenList)
        }
    }
}