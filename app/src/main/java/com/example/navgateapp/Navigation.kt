package com.example.navgateapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.lang.reflect.Modifier


@Composable
fun Navigaton() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(route = Screen.MainScreen.route){
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route+"/{name}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "MyoHset"
                    nullable = true
                }
            )
        ){  entry ->
            entry.arguments?.getString("name")?.let { DetailScreen(name = it,navController) }

        }

        composable(route = Screen.DialogScreen.route){
            DialogUI(navController = navController)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (navController: NavController){
    var text by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
    ){
        TextField(value = text, onValueChange ={
            text = it
        } ,modifier = androidx.compose.ui.Modifier
                .fillMaxWidth())
        Spacer( modifier = androidx.compose.ui.Modifier.height(8.dp))

        Button(
            onClick = {
                if (text.isEmpty()){
                    navController.navigate(Screen.DialogScreen.route)

                }else{
                    navController.navigate(Screen.DetailScreen.withArgs(text))
                }
            },
            ){
            Text(text="To DetailScreen")

        }

    }


}

@Composable
fun DetailScreen (name: String,navController : NavController){
    Box(
        contentAlignment = Alignment.Center,
        modifier = androidx.compose.ui.Modifier.fillMaxSize()

    ){
        Column {
            Text(text="Hello, $name")
            Button(
                onClick = {
                    navController.navigate(Screen.MainScreen.route)
                },
                ){
                Text(text="To HomeScreen")
            }
        }
    }

}

@Composable

fun DialogUI( navController: NavController) {
    val shouldShowDialog = remember{ mutableStateOf(true) }

    if ( shouldShowDialog.value){
        AlertDialog(
                onDismissRequest = { shouldShowDialog.value = false },
                title = { Text(text = "Dialog")},
                text = { Text(text = "Say Something")},
                confirmButton = {
                    Button( onClick = { shouldShowDialog.value = false
                        navController.navigate(Screen.MainScreen.route) },

                    ){
                        Text(text = "Comfirm", color = Color.White)
                    }
                })
    }
}




