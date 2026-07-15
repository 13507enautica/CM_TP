package com.example.adotanos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LogViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _estadoAuth = MutableLiveData<EstadoAuth>()
    val estadoAuth: LiveData<EstadoAuth> = _estadoAuth
    var estadoEmail: String=""

    fun verificarAuth() {
        if(auth.currentUser==null){
            _estadoAuth.value = EstadoAuth.Desconhecido
        }
        else {
            _estadoAuth.value = EstadoAuth.Autenticado
        }
    }
    init {
        verificarAuth()
    }

    fun realizarLogin(email:String, password:String) {

        if(email.isEmpty() || password.isEmpty()) {
            _estadoAuth.value = EstadoAuth.Erro("Escreva o seu email ou password.")
            return
        }

        _estadoAuth.value = EstadoAuth.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _estadoAuth.value = EstadoAuth.Autenticado
                    estadoEmail = email
                }
                else {
                    _estadoAuth.value = EstadoAuth.Erro(task.exception?.message?:"Houve um erro durante o seu login.")
                }
            }
    }

    fun realizarSignup(email:String, password:String, confPassword:String) {

        if(email.isEmpty() || password.isEmpty()) {
            _estadoAuth.value = EstadoAuth.Erro("Escreva o seu email ou password.")
            return
        }

        if(confPassword != password) {
            _estadoAuth.value = EstadoAuth.Erro("A password tem que ser a mesma em ambos os campos!")
            return
        }

        _estadoAuth.value = EstadoAuth.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _estadoAuth.value = EstadoAuth.Autenticado
                }
                else {
                    _estadoAuth.value = EstadoAuth.Erro(task.exception?.message?:"Houve um erro durante o seu login.")
                }
            }
    }

    fun realizarSignout() {
        auth.signOut()
        _estadoAuth.value = EstadoAuth.Desconhecido
    }
}

sealed class EstadoAuth {
    object Autenticado : EstadoAuth()
    object Desconhecido : EstadoAuth()
    object Loading : EstadoAuth()
    data class Erro(val message:String): EstadoAuth()
}