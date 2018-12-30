package utils

import model.Location
import model.Route
import model.SLEIGHT_CAPACITY

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

fun circularArea(locations: List<Location>, center: Location, radius: Int): List<Location> {
    return locations
            .filter { distance(center, it) <= radius }
}

fun closestCircularArea(Locationren: List<Location>, count: Int): List<Location> {
    return findCircularArea(Locationren, count, findClosest(KORVATUNTURI, Locationren))
}

fun furthestCircularArea(Locationren: List<Location>, count: Int): List<Location> {
    return findCircularArea(Locationren, count, findFurthest(KORVATUNTURI, Locationren))
}

fun findCircularArea(Locationren: List<Location>, count: Int, center: Location): List<Location> {
    var radius = km(10)
    var area = circularArea(Locationren, center, radius)

    while (area.size < count && area.size != Locationren.size) {
        radius += km(10)
        area = circularArea(Locationren, center, radius)
    }

    if (area.size > 200) {
        throw Exception("Too large area: ${area.size} with radius: ${radius}")
    }
    return area
}

fun findFurthest(location: Location, Locationren: List<Location>): Location =
        findByDistance(location, Locationren) { current, best -> current > best }

fun findClosest(location: Location, Locationren: List<Location>): Location =
        findByDistance(location, Locationren) { current, best -> current < best }


private fun findByDistance(location: Location, Locationren: List<Location>, comparator: (Double, Double) -> Boolean): Location {
    var bestMatch = Locationren[0]
    var bestDistance = distance(location, bestMatch)
    for (Location in Locationren) {
        val distance = distance(location, Location)
        if (comparator(distance, bestDistance)) {
            bestDistance = distance
            bestMatch = Location
        }
    }

    return bestMatch
}

fun routeLength(solution: List<Route>): Double {
    return solution
            .map { routeLength(it) }
            .fold(0.0) { acc, d -> acc + d }
}

fun routeLength(route: Route): Double {
    val locations = route.stops
    var length = 0.0
    var current = locations[0]
    for (location in locations.subList(1, locations.size)) {
        length += distance(current, location)
        current = location
    }
    return length
}

fun averageCapacityPercent(solution: List<Route>): Double {
    return solution
            .map {
                (it.stops.map { it.weight }.sum().toDouble()) / SLEIGHT_CAPACITY
            }
            .average()
}

fun getById(locations: List<Location>, id: Int): Location {
    return locations.filter { it.id == id }.get(0)
}