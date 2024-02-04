package com.nadaalshaibani.petsapi

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.nadaalshaibani.petsapi.model.Pet
import com.nadaalshaibani.petsapi.viewModel.PetViewModel


@Composable
fun PetsListScreen(viewModel: PetViewModel, padding: PaddingValues) {
    val pets = viewModel.pets
    LazyColumn(modifier = Modifier.padding(padding)) {
        items(pets) { pet ->
            PetItem(pet)
        }
    }
}

@Composable
fun PetItem(pet: Pet) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        shape = RoundedCornerShape(20.dp),elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )){
        Row(
            Modifier
                .padding(10.dp)
                .height(150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = pet.image,
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
            )

            Column (
                Modifier
                    .padding(10.dp)
                    .height(150.dp)
            ){
                Text(text = pet.name)
                Text(text = pet.age.toString())
                Text(text = pet.gender)
                Text(text = pet.adopted.toString())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(){
    val petViewModel: PetViewModel = viewModel()
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopAppBar(title = { Text(text ="My Pet List") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addPet") }) {
                Text(text = "+")
            }
        }
    ) { padding ->
        NavHost(navController = navController, startDestination = "petList") {
            composable("petList") {
                PetsListScreen(petViewModel, padding)
            }
            composable("addPet") {
                AddPetScreen()
            }
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPetScreen() {

    val petViewModel: PetViewModel = viewModel()


    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var adopted by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add a New Book",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            InputField(
                value = name,
                onValueChange = { name = it.toString() },
                label = "Name"
            )
            InputField(
                value = age,
                onValueChange = { age = it.toString() },
                label = "Age",
                keyboardType = KeyboardType.Number
            )
            InputField(
                value = gender,
                onValueChange = { gender = it.toString() },
                label = "Gender"
            )
            InputField(
                value = adopted,
                onValueChange = { adopted = it.toString() },
                label = "Adopted"
            )
            InputField(
                value = imageUrl,
                onValueChange = { imageUrl = it.toString() },
                label = "Image URL"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val newPet = Pet(
                        id = 0,  // Assuming ID is generated by the server
                        name = name,
                        age = age.toInt(),
                        gender = gender,
                        adopted = adopted.toBoolean(),
                        image = imageUrl
                    )
                    petViewModel.addPet(newPet)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Pet")
            }
        }
    }
}
@Composable
fun InputField(value: String, onValueChange: (String) -> Unit, label: String, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

