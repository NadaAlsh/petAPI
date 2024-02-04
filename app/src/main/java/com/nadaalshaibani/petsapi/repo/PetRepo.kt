package com.nadaalshaibani.petsapi.repo

import com.nadaalshaibani.petsapi.interfaceApi.PetApiService

class PetRepo(private val api: PetApiService) {
    suspend fun getAllPets() = api.getAllPets()
    // Other repository methods...
}
