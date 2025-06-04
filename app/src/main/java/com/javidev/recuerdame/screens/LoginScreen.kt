package com.javidev.recuerdame.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.javidev.recuerdame.R
import com.javidev.recuerdame.navigation.Screen

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {
        //define un usuario por defecto
        val defaultUser =
            com.javidev.recuerdame.data.User(username = "campusjalpa", password = "campusjalpa")
        //recuerda lo que escribe el usuario
        var inputUsername by remember { mutableStateOf("") }
        var inputPassword by remember { mutableStateOf("") }
        var loginError by remember { mutableStateOf(false) }
        val scrollState = rememberScrollState()//recuerda el scroll de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(16.dp)
                .verticalScroll(scrollState),//aplica el scroll, solo si el contenido no cabe en la pantalla
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.weight(1f))
            //titulo de la app
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 32.sp),
                color = Color.White
            )
            //logo de la app
            MegaCard()
            Spacer(Modifier.padding(16.dp))
            //eslogan de la app
            Text(
                text = stringResource(R.string.eslogan),
                fontWeight = FontWeight.Bold,
                style = TextStyle(fontSize = 16.sp),
                color = Color.White
            )
            Spacer(Modifier.padding(16.dp))
            //campo de entrada para el usuario
            OutlinedTextField(
                value = inputUsername,
                onValueChange = { inputUsername = it },
                label = { Text(stringResource(R.string.usuario)) },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray
                )
            )
            //campo de entrada para la contraseña
            OutlinedTextField(
                value = inputPassword,
                onValueChange = { inputPassword = it },
                label = { Text(stringResource(R.string.contraseña)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLeadingIconColor = Color.White,
                    unfocusedLeadingIconColor = Color.White,
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            //boton de login
            Button(
                onClick = {
                    if (inputUsername == defaultUser.username && inputPassword == defaultUser.password) {
                        loginError = false
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true } // Para que no se pueda volver con "back"
                        }
                    } else {
                        loginError = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3B82F6), // Azul
                    contentColor = Color.White          // Texto blanco
                )
            ) {
                Text(stringResource(R.string.iniciar_sesion))
            }
            //si la contraseña o el usuario son incorrectos manda un error en forma de texto
            if (loginError) {
                Text(
                    text = stringResource(R.string.error_login),
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }

//tajeta que compone el logo
@Composable
fun MegaCard(){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.alpha(0.5f)
    ){
        Card (
            modifier = Modifier
                .size(width = 40.dp, height = 40.dp), // Ajusta las dimensiones que quieras
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            shape = CircleShape
        ){
            Image(
                painter = painterResource(R.drawable.draw),
                contentDescription = "null",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp, vertical = 5.dp)
            )
        }
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card (
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(width = 40.dp, height = 40.dp), // Ajusta las dimensiones que quieras
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                ),
                shape = CircleShape,

            ){
                Image(
                    painter = painterResource(R.drawable.campana),
                    contentDescription = "null",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                )
            }

            Card (
                modifier = Modifier
                    .size(width = 200.dp, height = 200.dp), // Ajusta las dimensiones que quieras
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF424242)
                ),
                shape = CircleShape
            ){
                Image(
                    painter = painterResource(R.drawable.addnote),
                    contentDescription = "null",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                )
            }

            Card (
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 40.dp, height = 40.dp), // Ajusta las dimensiones que quieras
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                ),
                shape = CircleShape
            ){
                Image(
                    painter = painterResource(R.drawable.baseline_text_snippet_24),
                    contentDescription = "null",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal =5.dp, vertical = 5.dp)
                )
            }
        }
        Card (
            modifier = Modifier
                .size(width = 40.dp, height = 40.dp), // Ajusta las dimensiones que quieras
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            shape = CircleShape
        ){
            Image(
                painter = painterResource(R.drawable.baseline_alarm_add_24),
                contentDescription = "null",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp, vertical = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MegaCard()
}
