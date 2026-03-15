package org.example.cm.virtual_library

open class Book(val title:String,
                val author:String,
                val publicationYear:Int,
                val availableCopies:Int) {

    init { // verificacao de erros possiveis
        require(availableCopies>0) {"A quantidade não pode ser zero ou negativa."}
        require(publicationYear>0) {"O ano terá que ser um valor positivo."}
        require(publicationYear<2027) {"O livro não pode ser do futuro."}
    }

    var currentCopies: Int = availableCopies
        set(value) {
            if (value<0) {
                println("Sorry, $title is out of stock.")
                field = value + 1
            }
            else {
                if (value == 0) {
                    println("Warning: $title is now out of stock!")
                }
                field = value
            }
        }

    val era: String
        get() =
            when(publicationYear) {
                in 1..<1980 -> "Classic"
                in 1980..<2010 -> "Modern"
                in 2010..<2027 -> "Contemporary"
                else -> "Unknown"
            }

    init { // print final
        println("Book '$title' by $author has been added to the library. ")
    }

    override fun toString():String {
        return "Title: $title, Author: $author, Era: era, Available: $currentCopies copies."
    }
}

class DigitalBook(title:String,
                  author:String,
                  publicationYear:Int,
                  availableCopies:Int,
                  val fileSizeMB:Double,
                  val fileFormat:String) : Book(title,author,publicationYear,availableCopies) {

    // format

}

class PhysicalBook(title:String,
                  author:String,
                  publicationYear:Int,
                  availableCopies:Int,
                  val weight:Int,
                  val hasHardcover:Boolean = true) : Book(title,author,publicationYear,availableCopies) {

    // format

}

class Library(val name:String) {

    var bookInventory:MutableList<Book> = mutableListOf<Book>()

    companion object Compa {
        var numero_livros = 0
        fun getTotalBooksCreated():Int {
            return numero_livros
        }
    }

    fun addBook(book:Book) {
        bookInventory.add(book)
        numero_livros += 1
    }

    fun borrowBook(title:String) {
        for (book in bookInventory) {
            if (book.title == title) {
                book.currentCopies -= 1
                if (book.currentCopies>0)
                    println("Successfully borrowed '${book.title}'. Copies remaining: ${book.currentCopies}")
            }
        }
    }

    fun returnBook(title:String) {
        for (book in bookInventory) {
            if (book.title == title) {
                book.currentCopies += 1
                println("Book '${book.title}' returned successfully. Copies remaining: ${book.currentCopies}")
            }
        }
    }

    fun showBooks() {
        println("\n-- Library Catalog: --")
        for (book in bookInventory) {
            println(book)
        }
    }

    fun searchByAuthor(author:String) {
        for (book in bookInventory) {
            if (book.author == author) {
                println("- ${book.title} (${book.era}, ${book.currentCopies} copies available)")
            }
        }
    }
}

fun main() {
    val library = Library("Central Library")

    val digitalBook = DigitalBook(
        "Kotlin in Action",
        "Dmitry Jeremov",
        2017,
    5,
        4.5,
    "PDF")
    val physicalBook = PhysicalBook (
        "Clean Code",
        "Robert C. Martin",
        2008,
        3,
        650,
        true
    )
    val classicBook = PhysicalBook (
        "1984",
        "George Orwell",
        1949,
        2,
        400,
        false
    )

    library.addBook(digitalBook)
    library.addBook(physicalBook)
    library.addBook(classicBook)

    library.showBooks()

    println("\n--- Borrowing Books ---")
    library.borrowBook ("Clean Code")
    library.borrowBook ("1984")
    library.borrowBook ("1984")
    library.borrowBook ("1984") // Should fail - no copies left
    println("\n--- Returning Books ---")
    library.returnBook ("1984")
    println("\n--- Search by Author ---")
    library.searchByAuthor ("George Orwell")

}