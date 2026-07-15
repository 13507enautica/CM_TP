package com.example.adotanos.composables

// Código baseado em tutorial oficial de autenticação com Email/Passowrd com Firebase
// https://firebase.google.com/docs/auth/android/password-auth

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.adotanos.viewmodel.EstadoAuth
import com.example.adotanos.viewmodel.LogViewModel


@Composable
fun PaginaLogin(
    modifier: Modifier = Modifier,
    navigControl: NavController,
    logViewModel: LogViewModel) {

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    val estadoAuth = logViewModel.estadoAuth.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(estadoAuth.value) {
        when(estadoAuth.value) {
            is EstadoAuth.Autenticado -> navigControl.navigate("principal")
            is EstadoAuth.Erro -> Toast.makeText(context, (estadoAuth.value as EstadoAuth.Erro).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = "Insira os seus detalhes de login:", modifier = Modifier.padding(top = 160.dp))

        Text(text = "Email:", modifier = Modifier.padding(top = 30.dp, end = 230.dp))
        OutlinedTextField(
            value = userEmail,
            onValueChange = {userEmail=it},
            label={Text(text="Email do utilizador")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Text(text = "Password:", modifier = Modifier.padding(top = 30.dp, end = 200.dp))
        OutlinedTextField(
            value = userPassword,
            onValueChange = {userPassword=it},
            label={Text(text="Password do utilizador")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = {
                logViewModel.realizarLogin(userEmail, userPassword)
            },
            enabled=(estadoAuth.value!= EstadoAuth.Loading),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(150.dp))

        Text(text="Não tem uma conta?", fontSize = 20.sp)
        TextButton(onClick = { navigControl.navigate("signup") }) {
            Text(text = "Crie uma agora.", fontSize = 20.sp)
        }

    }
}