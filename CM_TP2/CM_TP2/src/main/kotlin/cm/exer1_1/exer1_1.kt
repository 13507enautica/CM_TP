package cm.exer1_1

sealed class Event
{
    data class Login(val username: String, val timestamp: Long): Event()
    data class Purchase(val username: String, val amount: Double, val timestamp: Long): Event()
    data class Logout(val username: String, val timestamp: Long): Event()

}
fun List<Event>.filterByUser(username: String): List<Event>
{
    return this.filter {
        when (it)
        {
            is Event.Login -> it.username == username
            is Event.Purchase -> it.username == username
            is Event.Logout -> it.username == username
        }
    }
}

fun List<Event>.totalSpent(username: String): Double
{
    val purchases: List<Event.Purchase> = this.filterIsInstance<Event.Purchase>()
    val filtered: List<Event.Purchase> = purchases.filter { it.username == username }

    return filtered.sumOf { it.amount }
}

fun processEvents(events: List<Event>, handler: (Event) -> Unit)
{
    events.forEach(handler)
}

fun main() {
    val events = listOf (
        Event.Login("Alice",1_000),
        Event.Purchase("Alice",49.99,1_100),
        Event.Purchase("Bob",19.99,1_200),
        Event.Login("Bob",1_050),
        Event.Purchase("Alice",15.00,1_300),
        Event.Logout("Alice",1_400),
        Event.Logout("Bob",1_500)
    )

    processEvents(events, {
        when (it)
        {
            is Event.Login -> println("[LOGIN]\t${it.username} logged in at t=${it.timestamp}")
            is Event.Purchase -> println("[PURCHASE]\t${it.username} spent $${it.amount} at t=${it.timestamp}")
            is Event.Logout -> println("[LOGOUT]\t${it.username} logged out at t=${it.timestamp}")
        }
    })
    val names: List<String> = listOf("Alice", "Bob")

    names.forEach {
        println("Total spent by $it: $${events.totalSpent(it)}")
    }
    println("Events for Alice:")

    processEvents(events.filterByUser("Alice"), {
        when (it)
        {
            is Event.Login -> println("\tLogin(username=${it.username}), timestamp=${it.timestamp})")
            is Event.Purchase -> println("\tPurchase(username=${it.username}, amount=${it.amount},timestamp=${it.timestamp})")
            is Event.Logout -> println("\tLogout(username=${it.username}), timestamp=${it.timestamp})")
        }
    }
    )

}