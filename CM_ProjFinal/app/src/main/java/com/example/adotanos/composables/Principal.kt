package com.example.adotanos.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.adotanos.data.Animal
import com.example.adotanos.data.RepositorioFirebase
import com.example.adotanos.viewmodel.EstadoAuth
import com.example.adotanos.viewmodel.LogViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.adotanos.R

@Composable
fun PaginaPrincipal(
    modifier: Modifier = Modifier,
    navigControl: NavController,
    logViewModel: LogViewModel) {

    val estadoAuth = logViewModel.estadoAuth.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(estadoAuth.value) {
        when(estadoAuth.value) {
            is EstadoAuth.Desconhecido -> navigControl.navigate("login")
            else -> Unit
        }
    }

    val animals = remember { mutableStateListOf<Animal>() }
    LaunchedEffect(Unit) {
        RepositorioFirebase.getAnimals {
            animals.clear()
            animals.addAll(it)
        }
    }

    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top,) {
        Box(
            modifier = Modifier.background(color=Color(0xFFFF6600)).padding(16.dp).fillMaxWidth(),

            ) {
            Image(
                painter=painterResource(if(isSystemInDarkTheme())R.drawable.adotanos_logo_light else R.drawable.adotanos_logo_dark),
                contentDescription = "icon")
            Text(text="Adotanos - Página Principal", modifier= Modifier.padding(start = 32.dp), fontWeight = FontWeight.SemiBold)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        TextButton(
            onClick={logViewModel.realizarSignout()
            }
        ) {
            Text(text="Terminar Sessão",color=Color.Red)
        }
        ExtendedFloatingActionButton(
            onClick = { navigControl.navigate("criar") },
            icon = { Icon(Icons.Default.Add, contentDescription = "Criar Animal")},
            text={Text(text="Adicionar Animal")},
            modifier= Modifier.padding(top=32.dp),
            containerColor=Color(0xFFFF6600)
            )
        Spacer(modifier= Modifier.height(32.dp))
        LazyColumn() {
            items(animals) { animal ->
                Card(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp).fillMaxWidth()
                    .clickable{ if (logViewModel.estadoEmail==animal.userEmail) navigControl.navigate("editar/${animal.id}/${animal.nome}/${animal.idade}/${animal.sexo}/${animal.localidade}/${animal.tipo}/${animal.descricao}/${animal.imagemId}/${animal.contactoEmail}/${animal.contactoTelemovel}/${animal.userEmail}")
                    else {Toast.makeText(context, "Não tem permissões para editar este animal.", Toast.LENGTH_SHORT).show()} },
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(12.dp)
                    ) {
                    Box(modifier= Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier= Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text=animal.nome, fontWeight = FontWeight.SemiBold, color=Color(0xFFFF6600))
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier.padding(horizontal=8.dp,vertical=8.dp)
                                .border(width=1.dp,color=Color(0xFFFF6600),shape= RoundedCornerShape(8.dp))
                                .padding(16.dp),
                                contentAlignment = Alignment.Center)
                            {
                                Image(
                                    painter=painterResource(animal.imagemId),
                                    contentDescription = "Imagem Animal",
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            Spacer(modifier=Modifier.height(8.dp))
                            Row(
                                modifier=Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text="Idade: ${animal.idade} anos\nSexo: ${animal.sexo}\nLocalidade: ${animal.localidade}\nTipo: ${animal.tipo}")
                                Text(text="'${animal.descricao}'", fontStyle = FontStyle.Italic, modifier=Modifier.padding(start=20.dp))
                            }
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text="\nInformação de contacto para adoção:\nEmail: ${animal.contactoEmail}\nTelemóvel: ${animal.contactoTelemovel}")
                            }
                        }
                    }
                }
            }
        }
    }
}