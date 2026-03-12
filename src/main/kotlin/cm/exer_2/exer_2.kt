package org.example.cm.exer_2

fun lerInputMenu() : Int? {
    print("""
     |
     |-- Indique uma opção: --
     |1 - Adição
     |2 - Subtração
     |3 - Multiplicação
     |4 - Divisão
     |
     |Escolha: 
    """.trimMargin())
    val escolha = verificarInputInt()
    return escolha
}

fun verificarInputInt() : Int? {
    try {
        var input:Int? = -1
        while (input !in 1..4) {
            input = readLine()?.toIntOrNull()
            require(input != null)
            if (input !in 1..4)
                println("Introduza um valor entre 1-4.")
        }

        println("O seu input foi $input")
        return input
    }
    catch (e:IllegalArgumentException) {
        println("O input indicado não é válido. (Erro: $e)")
        return -1
    }
}

fun main() {
    val numero1: Int = 1
    val numero2: Int = 0
    var resultado:Int? = null

    val escolha = lerInputMenu()

    try {
        when (escolha) {
            1 -> resultado = numero1 + numero2
            2 -> resultado = numero1 - numero2
            3 -> resultado = numero1 * numero2
            4 -> resultado = numero1 / numero2
            else -> resultado = null
        }
    }
    catch (e:ArithmeticException) {
        println("Tentativa a dividir por zero resulta num resultado impossível. (Erro: $e)")
    }
    println("Resultado = $resultado")
    println("Fim do programa.")
}