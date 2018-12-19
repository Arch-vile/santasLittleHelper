import java.io.File


fun main(args: Array<String>) {
    println("Hello, Santa!")

    val santasHelper = SantasLittleHelper(readChildren("src/nicelist.txt"))
    val routes: MutableList<List<Child>> = mutableListOf()

    while (santasHelper.pendingLocations() > 0) {
        santasHelper.scoutRoute()
        routes.add(santasHelper.clearRoute())
        println("One route found")
    }

    println("Santa, I am ready!")

}

fun readChildren(filename: String): List<Child> =
        File(filename).useLines { it.toList() }
                .map { it.split(";") }
                .map { Child(it[0], Location(it[1].toDouble(), it[2].toDouble()), it[3].toInt()) }

data class Location(val lat: Double, val lon: Double)

data class Child(val id: String, val location: Location, val giftWeight: Int)

class SantasLittleHelper(val world: List<Child>) {

    val children = world.toMutableList()
    val KORVATUNTURI = Location(68.073611, 29.315278)
    var currentLocation = KORVATUNTURI

    fun pendingLocations(): Int {
        return world.size
    }

    fun scoutRoute(): List<Child> {
        val route: MutableList<Child> = mutableListOf()
        var sleightIsFull = false
        var sleightWeight = 0

        while (!sleightIsFull) {
            val next = findNearest()
            if (sleightWeight + next.giftWeight <= 10000000) {
                sleightWeight += next.giftWeight
                currentLocation = next.location
                route.add(next)
                children.remove(next)
            } else {
                sleightIsFull = true
            }
        }

        return route.toList()
    }

    private fun findNearest(): Child {
        return world.get(0)
    }

    fun clearRoute(): List<Child> {
        return emptyList();
    }
}



