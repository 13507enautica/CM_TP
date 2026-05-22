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

    // -------------------------------------------------------
    // 1. Cache<String, Int> → word frequency counts
    // -------------------------------------------------------
    val wordCache = Cache<String, Int>()

    wordCache.put("kotlin", 3)
    wordCache.put("java", 5)
    wordCache.put("cobol", 2)

    // simulate frequency update using transform
    wordCache.transform("kotlin") { it + 1 }

    // getOrPut usage (existing key)
    val javaCount = wordCache.getOrPut("java") { 0 }

    // getOrPut usage (missing key)
    val scalaCount = wordCache.getOrPut("scala") { 1 }

    println("=== Word Frequency Cache ===")
    println("kotlin = ${wordCache.get("kotlin")}")
    println("java = $javaCount")
    println("scala = $scalaCount")
    println("size = ${wordCache.size()}")
    println("snapshot = ${wordCache.snapshot()}")

    println()


    // -------------------------------------------------------
    // 2. Cache<Int, String> → id-to-name registry
    // -------------------------------------------------------
    val userCache = Cache<Int, String>()

    userCache.put(1, "Alice")
    userCache.put(2, "Bob")
    userCache.put(3, "Charlie")

    // update value using transform
    userCache.transform(2) { it.uppercase() }

    // remove entry
    userCache.evict(3)

    // getOrPut examples
    val user1 = userCache.getOrPut(1) { "Unknown" }
    val user4 = userCache.getOrPut(4) { "David" }

    println("=== ID Registry Cache ===")
    println("1 = $user1")
    println("2 = ${userCache.get(2)}")
    println("3 = ${userCache.get(3)}")
    println("4 = $user4")
    println("size = ${userCache.size()}")
    println("snapshot = ${userCache.snapshot()}")

    // -------------------------------------------------------
    // Word frequency cache
    // -------------------------------------------------------

    wordCache.put("kotlin", 3)
    wordCache.put("java", 0)
    wordCache.put("cobol", 2)
    wordCache.put("scala", -1)

    println("=== Original Cache ===")
    println(wordCache.snapshot())

    // -------------------------------------------------------
    // Filter values > 0
    // -------------------------------------------------------
    val filtered = wordCache.filterValues { it > 0 }

    println("\n=== Filtered (values > 0) ===")
    filtered.forEach { (word, count) ->
        println("$word = $count")
    }
}