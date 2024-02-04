package com.nadaalshaibani.petsapi.model

data class Pet(
    val id: Int,
    val name: String,
    val adopted: Boolean,
    val image: String,
    val age: Int?,
    val gender: String
)
