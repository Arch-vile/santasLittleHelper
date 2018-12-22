package utils

import model.Child
import model.Location
import model.Route

fun km(value: Int) = value * 1000

/**
 * Distance in meters
 */
fun distance(location1: Location, location2: Location): Double {
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

fun circularArea(locations: List<Child>, center: Location, radius: Int): List<Child> {
    return locations
            .filter { distance(center, it.location) <= radius }
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

fun routeLength(solution: List<Route>): Double {
    return solution
            .map { routeLength(it) }
            .fold(0.0) { acc, d -> acc + d }
}

fun routeLength(route: Route): Double {
    var length = 0.0
    var current = KORVATUNTURI
    for (location in route.stops.map { it.location }) {
        length += distance(current, location)
        current = location
    }

    length += distance(current, KORVATUNTURI)
    return length
}
