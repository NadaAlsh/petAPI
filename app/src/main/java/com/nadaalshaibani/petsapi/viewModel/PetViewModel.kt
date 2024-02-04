package com.nadaalshaibani.petsapi.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadaalshaibani.petsapi.interfaceApi.PetApiService
import com.nadaalshaibani.petsapi.model.Pet
import com.nadaalshaibani.petsapi.repo.PetRepo
import com.nadaalshaibani.petsapi.singleton.RetrofitHelper
import kotlinx.coroutines.launch

class PetViewModel : ViewModel() {

    private val petApiService = RetrofitHelper.getInstance()
        .create(PetApiService::class.java)
    private val repository = PetRepo(petApiService)

    var pets by mutableStateOf(listOf<Pet>())
    var errorMsg: String? by mutableStateOf(null)
    var successMsg: String? by mutableStateOf(null)

    init {
        fetchPets()
    }

    fun fetchPets() {
        viewModelScope.launch {
            try {
                successMsg = "Successful"
                pets = repository.getAllPets()
            } catch (e: Exception) {
                println("error+ ${e}")
                errorMsg = e.toString()
                // Log the exception or handle the error
            }
        }
    }

    fun addPet(pet: Pet){
        viewModelScope.launch {
            try {
                var response = petApiService.addPet(pet)
                if (response.isSuccessful && response.body() != null){
                    successMsg = "Successful"
                }else{
                    errorMsg = "Error check your connection"
                }

            } catch (e: Exception){
                // Network Issue
            }
        }
    }
    fun deletePet(petID: Int){
        viewModelScope.launch {
            try {
                var response = petApiService.deletePet(petID)
                if(response.isSuccessful){

                }else{

                }

            } catch (e: Exception){

            }
        }
    }
}