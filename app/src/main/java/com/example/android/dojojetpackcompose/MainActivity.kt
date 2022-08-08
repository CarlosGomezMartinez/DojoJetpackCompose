package com.example.android.dojojetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android.dojojetpackcompose.ui.theme.DojoJetpackComposeTheme

class MainActivity : ComponentActivity() {

    data class Person(val name: String, val lastName: String, val city: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val persons by rememberSaveable { mutableStateOf(mutableListOf<Person>())    }
            DojoJetpackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background){
                    NavHost(navController = navController, startDestination = "LISTA"){
                        composable(route = "LISTA"){
                            myList(navController= navController, persons = persons)
                        }
                        composable(route = "FORM"){
                            form(navController = navController, persons = persons)
                        }
                    }
                    //myItem(name = "aaa", lastName = "dsdsd", city = "dsdsd")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DojoJetpackComposeTheme {
        Greeting("Android")
    }
}

@Composable
fun form(navController: NavHostController, persons: MutableList<MainActivity.Person>) {
    var name by rememberSaveable { mutableStateOf("")}
    var lastName by rememberSaveable { mutableStateOf("")}
    var city by rememberSaveable { mutableStateOf("")}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
            .fillMaxWidth()
    ) {
        myField(
            value = name,
            onValueChange = { name = it},
            placeholder = "Name",
            imageVector = Icons.Filled.Person,
            contentDescription = "Person"
        )
        Spacer(modifier = Modifier.height(4.dp))
        myField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = "LastName",
            imageVector = Icons.Filled.Person,
            contentDescription = "Person"
        )
        Spacer(modifier = Modifier.height(4.dp))
        myField(
            value = city,
            onValueChange = { city = it},
            placeholder = "City",
            imageVector = Icons.Filled.Person,
            contentDescription = "Location"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            persons.add(MainActivity.Person(name, lastName, city))
            navController.navigate("LISTA") }, contentPadding = PaddingValues(horizontal = 1.dp, vertical = 10.dp)) {
            Text(text = "Añadir")
        }
    }
}

@Composable
fun myField(value: String, onValueChange: (String) -> Unit, placeholder: String, imageVector: ImageVector, contentDescription: String){
    OutlinedTextField(value = value, onValueChange = onValueChange, placeholder = {
        Text(text = placeholder)
    }, leadingIcon = {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    })
}

@Composable
fun myItem(name: String, lastName: String, city: String){
    var count by rememberSaveable { mutableStateOf(0) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column() {
            Text("$name $lastName")
            Text("$city")
        }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { count++ }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")
            }
            Text(text = count.toString())
            
        }
    }
}

data class Person(val name: String, val lastName: String, val city: String)
@Composable
fun myList(navController: NavController, persons: MutableList<MainActivity.Person>){

    LazyColumn(){
        items (persons){
            person -> myItem(person.name, person.lastName, person.city)
        }
        
        item{
            Button(onClick = { navController.navigate("FORM") }) {
                Text(text = "Nuevo")
            }
        }
    }
}
