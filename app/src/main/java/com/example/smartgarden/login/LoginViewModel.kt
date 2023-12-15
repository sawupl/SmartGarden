package com.example.smartgarden.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel(private val auth: FirebaseAuth): ViewModel() {
    val isLoginLiveData = MutableLiveData<Boolean>()

    init {
        auth()
    }

    fun signIn(email: String, password: String) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                        isLoginLiveData.postValue(true)
                    }.await()
                }
                catch (e: Exception) {
                    isLoginLiveData.postValue(false)
                }
            }
        }

    private fun auth() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            isLoginLiveData.value = true
        }
    }
    }