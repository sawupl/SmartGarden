package com.example.smartgarden

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartgarden.login.LoginViewModel
import com.example.smartgarden.registration.RegistrationViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ViewModelFactory :
    ViewModelProvider.NewInstanceFactory() {

    private val viewModelHashMap = HashMap<String, ViewModel>()
    private val db = Firebase.firestore
    private val auth = Firebase.auth


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val key = LoginViewModel::class.java.name
            return if (viewModelHashMap.containsKey(key)) {
                getViewModel(key) as T
            } else {
                val viewModel: ViewModel = LoginViewModel(auth)
                addViewModel(key, viewModel)
                getViewModel(key) as T
            }
        }
        else if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            val key = RegistrationViewModel::class.java.name
            return if (viewModelHashMap.containsKey(key)) {
                getViewModel(key) as T
            } else {
                val viewModel: ViewModel = RegistrationViewModel(auth, db)
                addViewModel(key, viewModel)
                getViewModel(key) as T
            }
        }
        else if (modelClass.isAssignableFrom(EditGardenViewModel::class.java)) {
            val key = EditGardenViewModel::class.java.name
            return if (viewModelHashMap.containsKey(key)) {
                getViewModel(key) as T
            } else {
                val viewModel: ViewModel = EditGardenViewModel(db, auth)
                addViewModel(key, viewModel)
                getViewModel(key) as T
            }
        }
        else if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {
            val key = PlantViewModel::class.java.name
            return if (viewModelHashMap.containsKey(key)) {
                getViewModel(key) as T
            } else {
                val viewModel: ViewModel = PlantViewModel(db, auth)
                addViewModel(key, viewModel)
                getViewModel(key) as T
            }
        }
        else if (modelClass.isAssignableFrom(PlantInfoViewModel::class.java)) {
            val key = PlantInfoViewModel::class.java.name
            return if (viewModelHashMap.containsKey(key)) {
                getViewModel(key) as T
            } else {
                val viewModel: ViewModel = PlantInfoViewModel(db, auth)
                addViewModel(key, viewModel)
                getViewModel(key) as T
            }
        }
        else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val key = MainViewModel::class.java.name
            return if (viewModelHashMap.containsKey(key)) {
                getViewModel(key) as T
            } else {
                val viewModel: ViewModel = MainViewModel(db, auth)
                addViewModel(key, viewModel)
                getViewModel(key) as T
            }
        }
        else{
            throw ClassNotFoundException("нет такой ViewModel")
        }
    }

    private fun addViewModel(key: String, viewModel: ViewModel) {
        viewModelHashMap[key] = viewModel
    }

    private fun getViewModel(key: String): ViewModel? {
        return viewModelHashMap[key]
    }
}