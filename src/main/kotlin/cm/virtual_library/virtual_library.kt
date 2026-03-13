package org.example.cm.virtual_library

class Book(val title:String, val author:String, val publicationYear:Int, var availableCopies:Int) {
    init { // verificacao de erros possiveis
        require(availableCopies>0) {"Número de cópias tem que ser positivo.."}
        require(publicationYear>0) {"O ano terá que ser um valor positivo."}
        require(publicationYear<2027) {"O livro não pode ser do futuro."}
    }
    init { // print final
        println("'$title' by $author has been added to the library. ")
    }
}


fun main() {
    val book1 = Book("the", "theguy", 2027, 2)
}