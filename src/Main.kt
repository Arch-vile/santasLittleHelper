import java.io.File


fun main(args: Array<String>) {
    println("Hello, Santa!")

    val santasHelper = SantasLittleHelper(readChildren("src/nicelist.txt"))
    val routes: MutableList<List<Child>> = mutableListOf()

    while (santasHelper.pendingLocations() > 0) {
        routes.add(santasHelper.scoutRoute())
    }

    println("Santa, I am ready!")
    val out = File("output.csv")
    out.writeText("")
    routes.map { it.joinToString(separator = "; ") { it.id } }.forEach { out.appendText(it + "\n") }
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
        return children.size
    }

    fun scoutRoute(): List<Child> {
        val route: MutableList<Child> = mutableListOf()
        var sleightWeight = 0

        while (sleightWeight < 10000000 && children.isNotEmpty()) {
            val next = findNearest()
            sleightWeight += next.giftWeight

            if (sleightWeight < 10000000) {
                currentLocation = next.location
                route.add(next)
                children.remove(next)
            }
        }

        currentLocation = KORVATUNTURI
        println("Scouted for route with ${route.size} stops")
        println("${children.size} stops remaining")
        return route.toList()
    }

    private fun findNearest(): Child {

        var nearest = children[0]
        var nearestDistance = Float.MAX_VALUE
        for (child in children) {
            val distance = distance(currentLocation, child.location)
            if (distance < nearestDistance) {
                nearestDistance = distance
                nearest = child
            }
        }

        return nearest
    }

    private fun distance(location1: Location, location2: Location): Float {
        val lat1 = location1.lat
        val lng1 = location1.lon
        val lat2 = location2.lat
        val lng2 = location2.lon
        val earthRadius = 6378000
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLng / 2) * Math.sin(dLng / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return (earthRadius * c).toFloat()
    }

}



