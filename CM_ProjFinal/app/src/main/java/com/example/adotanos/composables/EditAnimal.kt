package com.example.adotanos.composables

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.adotanos.R
import com.example.adotanos.data.Animal
import com.example.adotanos.data.RepositorioFirebase
import com.example.adotanos.viewmodel.EstadoAuth
import com.example.adotanos.viewmodel.LogViewModel


@Composable
fun PaginaEditarAnimal(
    modifier: Modifier = Modifier,
    navigControl: NavController,
    logViewModel: LogViewModel,
    animal: Animal
) {

    val estadoAuth = logViewModel.estadoAuth.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(estadoAuth.value) {
        when(estadoAuth.value) {
            is EstadoAuth.Desconhecido -> navigControl.navigate("login")
            else -> Unit
        }
    }
    var animalNome by remember { mutableStateOf(animal.nome) }
    var animalIdade by remember { mutableStateOf(animal.idade) }
    var animalSexo by remember { mutableStateOf(animal.sexo) }
    var animalLocalidade by remember { mutableStateOf(animal.localidade) }
    var animalTipo by remember { mutableStateOf(animal.tipo) }
    var animalDescricao by remember { mutableStateOf(animal.descricao) }
    var animalImagemId by remember { mutableIntStateOf(animal.imagemId) }
    var contactoEmail by remember { mutableStateOf(animal.contactoEmail) }
    var contactoTele by remember { mutableStateOf(animal.contactoTelemovel) }

    var expandirSexo by remember { mutableStateOf(false) }
    var expandirTipo by remember { mutableStateOf(false) }
    var expandirImagens by remember { mutableStateOf(false) }

    val emailRegex = remember {
        Regex("^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}\$")
    }
    val telemovelRegex = remember {
        Regex("^9[0-9]{8}+\$")
    }

    if (expandirImagens) {
        val listaImagens:List<Int> = listOf(
            R.drawable.cao1,
            R.drawable.gato1,
            R.drawable.passaro1,
            R.drawable.coelho1,
            R.drawable.reptil1,
            R.drawable.peixe1,
        )

        Dialog(
            onDismissRequest = {expandirImagens = false}
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(listaImagens) { imagemItem ->
                    Box(modifier = Modifier.padding(4.dp)
                        .clickable{
                            animalImagemId=imagemItem
                            expandirImagens = false
                        },
                    ) {
                        Image(
                            painter = painterResource(id = imagemItem),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Editar informação de animal:", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.SemiBold)

        ExtendedFloatingActionButton(
            onClick = { expandirImagens = true },
            icon = { Icon(Icons.Default.Add, contentDescription = "Imagem")},
            text={Text(text="Imagem")}
        )

        Text(text = "Nome:")
        OutlinedTextField(
            value = animalNome,
            onValueChange = {animalNome=it},
            label={Text(text="Nome do animal")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        )
        Spacer(Modifier.height(20.dp))
        Text(text = "Idade:")
        OutlinedTextField(
            value = animalIdade,
            onValueChange = {
                if (it.isDigitsOnly() && it.length<3 && !it.startsWith("0"))  {
                    animalIdade=it
                }
            },
            label={Text(text="Idade do animal")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(Modifier.height(20.dp))
        Text(text = "Sexo: $animalSexo")
        Box(){
            IconButton(onClick = { expandirSexo = !expandirSexo }) {
                Icon(Icons.Default.Add, contentDescription = "Sexo do animal")
            }
            DropdownMenu(
                expanded = expandirSexo,
                onDismissRequest = { expandirSexo = false },
            ) {
                DropdownMenuItem(
                    text = { Text("Masculino") },
                    onClick = {
                        animalSexo="Masculino"
                        expandirSexo = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Femenino") },
                    onClick = {
                        animalSexo="Femenino"
                        expandirSexo = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("N/A") },
                    onClick = {
                        animalSexo="NA"
                        expandirSexo = false
                    }
                )
            }
        }
        Text(text = "Tipo: $animalTipo")
        Box(){
            IconButton(onClick = { expandirTipo = !expandirTipo }) {
                Icon(Icons.Default.Add, contentDescription = "Tipo de animal")
            }
            DropdownMenu(
                expanded = expandirTipo,
                onDismissRequest = { expandirTipo = false },
            ) {
                DropdownMenuItem(
                    text = { Text("Cão") },
                    onClick = {
                        animalTipo="Cão"
                        expandirTipo = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Gato") },
                    onClick = {
                        animalTipo="Gato"
                        expandirTipo = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Pássaro") },
                    onClick = {
                        animalTipo="Pássaro"
                        expandirTipo = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Coelho") },
                    onClick = {
                        animalTipo="Coelho"
                        expandirTipo = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Reptil") },
                    onClick = {
                        animalTipo="Reptil"
                        expandirTipo = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Peixe") },
                    onClick = {
                        animalTipo="Peixe"
                        expandirTipo = false
                    }
                )
            }
        }
        Text(text = "Localidade:")
        OutlinedTextField(
            value = animalLocalidade,
            onValueChange = {animalLocalidade=it},
            label={Text(text="Localidade do animal")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        )
        Spacer(Modifier.height(20.dp))
        Text(text = "Descrição:")
        OutlinedTextField(
            value = animalDescricao,
            onValueChange = {animalDescricao=it},
            label={Text(text="Descrição do animal")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        )
        Spacer(Modifier.height(20.dp))
        Text(text = "Descrição:")
        OutlinedTextField(
            value = animalDescricao,
            onValueChange = {animalDescricao=it},
            label={Text(text="Descrição do animal")},
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        )
        Spacer(Modifier.height(20.dp))
        Text(text = "Contacto email:")
        OutlinedTextField(
            value = contactoEmail,
            onValueChange = {contactoEmail=it},
            label={Text(text="Email de contacto")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )
        Spacer(Modifier.height(20.dp))
        Text(text = "Contacto telemóvel:")
        OutlinedTextField(
            value = contactoTele,
            onValueChange ={
                if (it.isDigitsOnly() && it.length<10)  {
                    contactoTele=it
                }
            },
            label={Text(text="Telemovel de contacto")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Spacer(Modifier.height(20.dp))


        Button(
            onClick = {

                if (animalNome.isEmpty() ||animalIdade.isEmpty() ||animalSexo.isEmpty() ||
                    animalLocalidade.isEmpty() ||animalTipo.isEmpty() ||animalDescricao.isEmpty()||
                    animalImagemId==-1||contactoEmail.isEmpty()||contactoTele.isEmpty()
                    ) {
                    Toast.makeText(context, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                }
                else if (!contactoEmail.matches(emailRegex) || !contactoTele.matches(telemovelRegex)  ) {
                    Toast.makeText(context, "Informação de contacto inválida.", Toast.LENGTH_SHORT).show()
                }
                else {
                    RepositorioFirebase.updateAnimal(Animal(
                        nome = animalNome,
                        idade = animalIdade,
                        sexo = animalSexo,
                        localidade = animalLocalidade,
                        tipo = animalTipo,
                        descricao = animalDescricao,
                        imagemId= animalImagemId,
                        contactoEmail=contactoEmail,
                        contactoTelemovel = contactoTele,
                        userEmail = logViewModel.estadoEmail,
                        id = animal.id,
                    ))
                    navigControl.popBackStack()
                    Toast.makeText(context, "Animal atualizado.", Toast.LENGTH_SHORT).show()
                }

            },
            ) {
            Text(text = "Atualizar animal")
        }
        Button(
            onClick={
                RepositorioFirebase.deleteAnimal(animal.id)
                navigControl.popBackStack()
                Toast.makeText(context, "Animal apagado.", Toast.LENGTH_SHORT).show()
                    },
            colors=ButtonDefaults.buttonColors(containerColor=Color.Red)
        ) { Text(text = "Apagar animal") }
    }


}