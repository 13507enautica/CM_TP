package org.example.cm.exer_1

fun main() {

    val exemplo1 = IntArray(50) {(it+1)*(it+1)}
    val exemplo2 = (1..50).map {it*it}
    val exemplo3 = Array(50) {(it+1)*(it+1)}
    println(exemplo1.joinToString(" "))
    println(exemplo2.joinToString(" "))
    println(exemplo3.joinToString(" "))

}