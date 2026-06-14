package cm.exer1_2

class Cache<K: Any, V: Any>
{

    private val storage: MutableMap<K, V> = mutableMapOf()

    fun put(key: K, value: V) {
        storage[key] = value
    }

    fun get(key: K): V? = storage[key]

    fun evict(key: K) {
        storage.remove(key)
    }

    fun size(): Int = storage.size

    fun getOrPut(key: K, default: () -> V): V {

        val v: V? = storage[key]
        if (v != null)
            return v

        val computedValue: V = default()

        storage[key] = computedValue
        return computedValue
    }

    fun transform(key: K, action: (V) -> V): Boolean {
        val v: V = storage[key] ?: return false

        val computedValue: V = action(v)
        storage[key] = computedValue
        return true
    }

    fun snapshot(): Map<K, V> = storage.toMap()

    fun filterValues(predicate: (V) -> Boolean): Map<K, V> {
        val copy: MutableMap<K, V> = mutableMapOf()
        storage.forEach {
            if (predicate(it.value))
                copy[it.key] = it.value
        }
        return copy
    }
}

fun main()
{

    val wordCache = Cache<String, Int>()
    wordCache.put("kotlin", 3)
    wordCache.put("java", 5)
    wordCache.put("cobol", 2)

    wordCache.transform("kotlin") { it + 1 }

    val javaCount = wordCache.getOrPut("java") { 0 }
    val scalaCount = wordCache.getOrPut("scala") { 1 }

    println("-- Word frequency cache -")
    println("kotlin = ${wordCache.get("kotlin")}")
    println("java = $javaCount")
    println("scala = $scalaCount")
    println("size = ${wordCache.size()}")
    println("snapshot = ${wordCache.snapshot()}")

    println()


    wordCache.put("kotlin", 3)
    wordCache.put("java", 0)
    wordCache.put("cobol", 2)
    wordCache.put("scala", -1)

    println("-- Cache antes de filtro --")
    println(wordCache.snapshot())


    val filtered = wordCache.filterValues { it > 0 }

    println("\n-- Cache depois de filtro (it>0) --")
    filtered.forEach { (word, count) ->
        println("$word = $count")
    }
}