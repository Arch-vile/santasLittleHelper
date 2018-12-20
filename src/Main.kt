import java.io.File


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

        println("%f".format(distance(Location(25.976517, -80.183687), Location(2.818585, 44.11212))))
        System.exit(1)


        val route: MutableList<Child> = mutableListOf()
        var sleightWeight = 0
        var routeLength: Double = 0.0

        while (sleightWeight < 10000000 && children.isNotEmpty()) {
            val next = findNearest()
            routeLength += distance(currentLocation, next.location)
            sleightWeight += next.giftWeight

            if (sleightWeight < 10000000) {
                currentLocation = next.location
                route.add(next)
                children.remove(next)
            }
        }

        routeLength += distance(currentLocation, KORVATUNTURI)
        currentLocation = KORVATUNTURI
        println("Scouted for route with ${route.size} stops")
        println("${children.size} stops remaining")
        return Route(route.toList(), routeLength)
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
        pr(lat1)
        val lng1 = location1.lon
        pr(lng1)
        val lat2 = location2.lat
        pr(lat2)
        val lng2 = location2.lon
        pr(lng2)
        val earthRadius = 6378000.0
        val dLat = Math.toRadians(lat2 - lat1)
        pr(dLat)
        val dLng = Math.toRadians(lng2 - lng1)
        pr(dLng)
        val a = Math.sin(dLat / 2.0) * Math.sin(dLat / 2.0) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2.0) * Math.sin(dLng / 2.0)
        pr(a)
        val c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a))
        pr(c)
        return (earthRadius * c)
    }

    private fun pr(value: Double) {
        println("%f".format(value))
    }

}



