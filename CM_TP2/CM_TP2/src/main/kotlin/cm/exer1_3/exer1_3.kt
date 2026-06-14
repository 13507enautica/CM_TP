package cm.exer1_3


class Pipeline()
{
    private val functions: MutableList<Pair<String, (List<String>) -> List<String>>> = mutableListOf()

    fun addStage(name: String, transform: (List<String>) -> (List<String>))
    {
        val pair = Pair(name, transform)
        functions.add(pair)
    }

    fun execute(input: List<String>): List<String>
    {
        var newList: List<String> = input

        functions.forEach {
            newList = it.second(newList)
        }

        return newList
    }

    fun describe()
    {
        functions.forEachIndexed { index, pair ->
            println("\t${index + 1}. ${pair.first}")
        }
    }

    infix fun <T> ((T) -> T).andThen(next: (T) -> T): (T) -> T = { next(this(it)) }

    fun compose(firstName: String, secondName: String, newName: String)
    {
        val first = functions.find { it.first == firstName } ?: error("Stage '$firstName' not found")
        val second = functions.find { it.first == secondName } ?: error("Stage '$secondName' not found")

        val composed = first.second andThen second.second
        addStage(newName, composed)
    }
}


fun buildPipeline(functions: Pipeline.() -> Unit): Pipeline
{
    val pipeline = Pipeline()

    functions.invoke(pipeline)

    return pipeline
}

fun fork(input: List<String>, pipe1: Pipeline, pipe2: Pipeline): Pair<List<String>, List<String>>
{
    return Pair(pipe1.execute(input), pipe2.execute(input))
}


fun trim(input: List<String>): List<String> = input.map { it.trim() }
fun filterErrors(input: List<String>): List<String> = input.filter  {"ERROR" in it}
fun makeUpper(input: List<String>): List<String> = input.map { it.uppercase() }
fun addIndex(input: List<String>): List<String> = input.mapIndexed { index, string -> "${index + 1}. $string" }



fun main() {

    val rawLogs = listOf(
        " INFO : server started ",
        " ERROR : disk full ",
        " DEBUG : checking config ",
        " ERROR : out of memory ",
        " INFO : request received ",
        " ERROR : connection timeout "
    )

    println("Introduzido:")
    rawLogs.forEachIndexed { i, v ->
        println("$i: \"$v\"")
    }

    val firstPipeline = buildPipeline {
        addStage("Trim", ::trim)
        addStage("FilterErrors", ::filterErrors)
        addStage("MakeUpper", ::makeUpper)
        addStage("AddIndex", ::addIndex)
    }

    val secondPipeline = buildPipeline {
        addStage("Trim", ::trim)
        addStage("FilterErrors", ::filterErrors)
    }

    println("\nPipeline 1:")
    val result1 = firstPipeline.execute(rawLogs)
    result1.forEach { println(it) }

    println("\nPipeline 2:")
    val result2 = secondPipeline.execute(rawLogs)
    result2.forEach { println(it) }

    println("\n-- Fork de Pipelines --")
    val (fork1, fork2) = fork(rawLogs, firstPipeline, secondPipeline)

    println("Resultado Fork 1:")
    fork1.forEach { println(it) }

    println("\nResultado Fork 2:")
    fork2.forEach { println(it) }

    println("\nPipeline Stages:")
    firstPipeline.describe()
}