package cm.exer1_1

sealed class Event {
    class Login(val username: String, val timestamp: Long): Event()
    class Purchase(val username: String, val amount: Double, val timestamp: Long): Event()
    class Logout(val username: String, val timestamp: Long): Event()
}

fun List<Event>.filterByUser(username: String): List<Event> {

    var lista_filtrada : MutableList<Event> = mutableListOf<Event>()

    this.forEach {
        System.out.println(it)

    }

    return lista_filtrada
}


fun main() {

    val events = listOf(
        Event.Login ("alice", 1_000),
        Event.Purchase ("alice", 49.99, 1_100),
        Event.Purchase ("bob", 19.99, 1_200),
        Event.Login ("bob", 1_050),
        Event.Purchase ("alice", 15.00, 1_300),
        Event.Logout ("alice", 1_400),
        Event.Logout ("bob", 1_500)
    )

    events.filterByUser("alice")


}