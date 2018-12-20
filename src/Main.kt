import java.io.File
import java.util.*


fun main(args: Array<String>) {
    println("Hello, Santa!")


    val santasHelper = SantasLittleHelper(readChildren("src/nicelist.txt"))
    val routes: MutableList<Route> = mutableListOf()

    while (santasHelper.pendingLocations() > 0) {
        routes.add(santasHelper.scoutRoute())
    }

    println("Santa, I am ready!")
    val out = File("output.csv")
    out.writeText("")
    routes.map { it.stops.joinToString(separator = "; ") { it.id } }.forEach { out.appendText(it + "\n") }

    val routeLength = routes.map { it.length }.fold(0.0) { acc, d -> acc + d }
    println("Route length $routeLength")
}

fun readChildren(filename: String): List<Child> =
        File(filename).useLines { it.toList() }
                .map { it.split(";") }
                .map { Child(it[0], Location(it[1].toDouble(), it[2].toDouble()), it[3].toInt()) }

data class Location(val lat: Double, val lon: Double)

data class Child(val id: String, val location: Location, val giftWeight: Int)

data class Route(val stops: List<Child>, val length: Double)

class SantasLittleHelper(val world: List<Child>) {

    val children = world.toMutableList()
    val KORVATUNTURI = Location(68.073611, 29.315278)
    var currentLocation = KORVATUNTURI

    fun pendingLocations(): Int {
        return children.size
    }

    fun scoutRoute(): Route {
        val targetArea = nextArea()
        val route = optimizeRoute(targetArea)

        currentLocation = KORVATUNTURI
        println("Scouted for route with ${route.stops.size} stops")
        println("${children.size} stops remaining")
        return route
    }

    private fun optimizeRoute(targetArea: List<Child>): Route {

        val numbers = listOf<Int>(1, 2, 3, 4)


        permutations(numbers, { s -> println(s.toArray()) })
        System.exit(1)




        return Route(emptyList(), 0.0)
    }

    fun permutations(items: List<Int>, handler: (Stack<Int>) -> Unit) {
        return permutations(items.toMutableList(), Stack<Int>(), items.size, handler)
    }


    fun permutations(items: MutableList<Int>, permutation: Stack<Int>, size: Int, handler: (Stack<Int>) -> Unit) {

        /* permutation stack has become equal to size that we require */
        if (permutation.size == size) {
            /* print the permutation */
            println(Arrays.toString(permutation.toTypedArray()))
        }

        /* items available for permutation */
        val availableItems = items.toIntArray()
        for (i in availableItems) {
            /* add current item */
            permutation.push(i)

            /* remove item from available item set */
            items.remove(i)

            /* pass it on for next permutation */
            permutations(items, permutation, size, handler)

            /* pop and put the removed item back */
            items.add(permutation.pop())
        }
    }

    private fun nextArea(): List<Child> {
        return emptyList()
    }

    private fun findFurthest(): Child {
        var furthest = children[0]
        var furthestDistance = Double.MIN_VALUE
        for (child in children) {
            val distance = distance(currentLocation, child.location)
            if (distance > furthestDistance) {
                furthestDistance = distance
                furthest = child
            }
        }

        return furthest
    }

    private fun findNearest(): Child {
        var nearest = children[0]
        var nearestDistance = Double.MAX_VALUE
        for (child in children) {
            val distance = distance(currentLocation, child.location)
            if (distance < nearestDistance) {
                nearestDistance = distance
                nearest = child
            }
        }

        return nearest
    }

    private fun distance(location1: Location, location2: Location): Double {
        val lat1 = location1.lat
        val lng1 = location1.lon
        val lat2 = location2.lat
        val lng2 = location2.lon
        val earthRadius = 6378000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2.0) * Math.sin(dLng / 2.0)
        val c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a))
        return (earthRadius * c)
    }

}



