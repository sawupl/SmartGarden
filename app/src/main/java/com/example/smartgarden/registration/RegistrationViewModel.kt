package com.example.smartgarden.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegistrationViewModel(private val auth: FirebaseAuth, private val db: FirebaseFirestore): ViewModel() {
    val isLoginLiveData = MutableLiveData<Boolean>()


    fun registration(email: String, password: String, login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    addUserInDatabase(login, auth.currentUser?.uid)
                    isLoginLiveData.postValue(true)
                }.await()
            }
            catch (e: Exception) {
                isLoginLiveData.postValue(false)
            }
    }
        }
    private fun addUserInDatabase(login: String, uid: String?) {
        val user = hashMapOf(
            "login" to login
        )
        db.collection("users")
            .document(uid.toString())
            .set(user)
    }
}