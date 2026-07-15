package com.example.adotanos.composables

// Código baseado em tutorial oficial de autenticação com Email/Passowrd com Firebase
// https://firebase.google.com/docs/auth/android/password-auth


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.adotanos.ui.theme.AdotanosTheme
import com.example.adotanos.viewmodel.LogViewModel
import com.example.adotanos.viewmodel.EstadoAuth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth


@Composable
fun PaginaSignup(
    modifier: Modifier = Modifier,
    navigControl: NavController,
    logViewModel: LogViewModel) {

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

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

        Text(text = "Preencha os seguintes campos para criar a sua conta:", modifier = Modifier.padding(8.dp))

        Text(text = "Email:", modifier = Modifier.padding(top = 50.dp, end = 230.dp))
        OutlinedTextField(
            value = userEmail,
            onValueChange = {userEmail=it},
            label={Text(text="Email do utilizador")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Text(text = "Password (6 ou mais caracteres c/ números):", modifier = Modifier.padding(top = 50.dp, end = 100.dp))
        OutlinedTextField(
            value = userPassword,
            onValueChange = {userPassword=it},
            label={Text(text="Password do utilizador")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Text(text = "Confirmar password:", modifier = Modifier.padding(top = 50.dp, end = 120.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {confirmPassword=it},
            label={Text(text="Confirmar password")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Button(
            onClick = { logViewModel.realizarSignup(userEmail, userPassword, confirmPassword) },
            enabled=(estadoAuth.value!= EstadoAuth.Loading),
            modifier = Modifier.padding(top = 30.dp, bottom = 20.dp)) {
            Text(text = "Criar conta")
        }
    }
}