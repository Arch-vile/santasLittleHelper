import java.awt.Color.red
import java.io.File
import java.util.*


fun main(args: Array<String>) {
    println("Hello, Santa!")


    val santasHelper = SantasLittleHelper(readChildren("src/nicelist.txt"))
    val routes: MutableList<Route> = mutableListOf()

    //while (santasHelper.pendingLocations() > 0) {
        routes.add(santasHelper.scoutRoute())
    //}

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
    var sleightCapacity = 10000000
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

    // TODO: Targetarea must contain only amount of presents that can fit
    // We could probably optimize this by making target area bigger but this needs to be taken into account in the permutations
    // otherwise we will have multiple permutations with the same first N items that will fill the sleight so we have
    // done unnecessary calculations
    private fun optimizeRoute(targetArea: List<Child>): Route {

        var bestScore = Double.MIN_VALUE
        var bestRoute = emptyList<Child>()
        var routesEvaluated = 0

        permutations(targetArea) { s ->
            val route = s.toMutableList()
            // Add korvatunturi as the first and last location
            route.add(0, Child("joulupukki", KORVATUNTURI, 0))
            route.add(Child("joulupukki", KORVATUNTURI, 0))

            val score = valueFunction(route)
            if (score > bestScore) {
                bestScore = score
                bestRoute = route
            }
            routesEvaluated++
            if(routesEvaluated % 10000 == 0)
            println("Routes evaluated: $routesEvaluated")
        }

        return Route(bestRoute, routeLength(bestRoute))
    }

    // TODO: If target area will be bigger then sleight can handle value function should prioritize amount of stops??
    private fun valueFunction(route: List<Child>): Double {
        val routeLength = routeLength(route)
        return Double.MIN_VALUE + routeLength
    }

    private fun routeLength(route: List<Child>): Double {
        var length = 0.0
        for(i in 0 until route.size-1) {
            length += distance(route[i].location, route[i+1].location)
        }

        return length
    }

    fun permutations(items: List<Child>, handler: (Stack<Child>) -> Unit) {
        println("Finding ${factorial(items.size)} permutations")
        return permutations(items.toMutableList(), Stack(), items.size, handler)
    }

    fun factorial(number: Int): Long {
        var result: Long = 1

        for (factor in 2..number) {
            result *= factor.toLong()
        }

        return result
    }
    fun permutations(items: MutableList<Child>, permutation: Stack<Child>, size: Int, handler: (Stack<Child>) -> Unit) {

        /* permutation stack has become equal to size that we require */
        if (permutation.size == size) {
            /* print the permutation */
            //println(Arrays.toString(permutation.toTypedArray()))
            handler(permutation)
        }

        /* items available for permutation */
        val availableItems = items.toTypedArray()
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

        val remaining = world.toMutableList()
        val center = findNearest(KORVATUNTURI, remaining)
        remaining.remove(center)

        val area = mutableListOf(center)

        var weight = center.giftWeight
        while(weight < sleightCapacity) {

            val next = findNearest(center.location, remaining)
            weight += next.giftWeight

            if(weight <= sleightCapacity) {
                area.add(next)
                remaining.remove(next)
            }
        }

        println("Determined next area for present delivery with ${area.size} children")
        return area
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

    private fun findNearest(location: Location, children: List<Child>): Child {
        var nearest = children[0]
        var nearestDistance = Double.MAX_VALUE
        for (child in children) {
            val distance = distance(location, child.location)
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



