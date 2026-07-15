package com.example.adotanos


import com.example.adotanos.composables.PaginaLogin
import com.example.adotanos.composables.PaginaPrincipal
import com.example.adotanos.composables.PaginaSignup
import com.example.adotanos.viewmodel.LogViewModel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.adotanos.composables.PaginaCriarAnimal
import com.example.adotanos.composables.PaginaEditarAnimal
import com.example.adotanos.data.Animal
import kotlin.String


@Composable
fun NavigationController(modifier: Modifier = Modifier, logViewModel: LogViewModel) {
    val navigControl = rememberNavController()
    NavHost(
        navController = navigControl,
        startDestination = "login",
        builder = {
            composable("login") {
                PaginaLogin(modifier, navigControl, logViewModel)
            }
            composable("signup") {
                PaginaSignup(modifier, navigControl, logViewModel)
            }
            composable("principal") {
                PaginaPrincipal(modifier, navigControl, logViewModel)
            }
            composable("criar") {
                PaginaCriarAnimal(modifier, navigControl, logViewModel)
            }
            composable(
                route="editar/{id}/{nome}/{idade}/{sexo}/{localidade}/{tipo}/{descricao}/{imagemId}/{contactoEmail}/{contactoTelemovel}/{userEmail}",
                arguments=listOf(
                    navArgument("id"){type= NavType.StringType},
                    navArgument("nome"){type= NavType.StringType},
                    navArgument("idade"){type= NavType.StringType},
                    navArgument("sexo"){type= NavType.StringType},
                    navArgument("localidade"){type= NavType.StringType},
                    navArgument("tipo"){type= NavType.StringType},
                    navArgument("descricao"){type= NavType.StringType},
                    navArgument("imagemId"){type= NavType.IntType},
                    navArgument("contactoEmail"){type= NavType.StringType},
                    navArgument("contactoTelemovel"){type= NavType.StringType},
                    navArgument("userEmail"){type= NavType.StringType},
                    )
            ) { backStackEntry ->
                    val args = backStackEntry.arguments!!
                PaginaEditarAnimal(modifier, navigControl, logViewModel, Animal(
                    id = args.getString("id")?:"",
                    nome = args.getString("nome")?:"",
                    idade = args.getString("idade")?:"",
                    sexo = args.getString("sexo")?:"",
                    localidade = args.getString("localidade")?:"",
                    tipo = args.getString("tipo")?:"",
                    descricao = args.getString("descricao")?:"",
                    imagemId = args.getInt("imagemId"),
                    contactoEmail = args.getString("contactoEmail")?:"",
                    contactoTelemovel = args.getString("contactoTelemovel")?:"",
                    userEmail = args.getString("userEmail")?:"",
                    ))
            }
        }
    )
}