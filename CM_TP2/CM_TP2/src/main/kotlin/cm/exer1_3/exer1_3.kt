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
        println("Starting Pipeline")

        functions.forEach {
            newList = it.second(newList)
            println("${it.first} applied -> ${newList.toString()}")
        }

        println("End of Pipeline")

        return newList
    }

    fun describe()
    {
        println("Stages names:")
        functions.forEachIndexed { index, pair ->
            println("\t${index + 1}. ${pair.first}")
        }
    }

    // Challenge :D
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

//    pipeline.functions()
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

    // Original data
    val rawLogs = listOf(
        " INFO : server started ",
        " ERROR : disk full ",
        " DEBUG : checking config ",
        " ERROR : out of memory ",
        " INFO : request received ",
        " ERROR : connection timeout "
    )

    println("=== RAW INPUT ===")
    rawLogs.forEachIndexed { i, v ->
        println("$i: \"$v\"")
    }

    // Pipeline A: full processing
    val fullPipeline = buildPipeline {
        addStage("Trim", ::trim)
        addStage("FilterErrors", ::filterErrors)
        addStage("MakeUpper", ::makeUpper)
        addStage("AddIndex", ::addIndex)
    }

    // Pipeline B: lighter debugging pipeline
    val debugPipeline = buildPipeline {
        addStage("Trim", ::trim)
        addStage("AddIndex", ::addIndex)
    }

    println("\n=== PIPELINE A (FULL) ===")
    val resultA = fullPipeline.execute(rawLogs)
    resultA.forEach { println(it) }

    println("\n=== PIPELINE B (DEBUG) ===")
    val resultB = debugPipeline.execute(rawLogs)
    resultB.forEach { println(it) }

    println("\n=== FORK TEST ===")
    val (forkA, forkB) = fork(rawLogs, fullPipeline, debugPipeline)

    println("Fork A result:")
    forkA.forEach { println(it) }

    println("\nFork B result:")
    forkB.forEach { println(it) }

    println("\n=== PIPELINE STAGES (A) ===")
    fullPipeline.describe()
}