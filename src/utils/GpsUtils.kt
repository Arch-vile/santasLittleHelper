package utils

import model.Child
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

fun circularArea(locations: List<Child>, center: Location, radius: Int): List<Child> {
    return locations
            .filter { distance(center, it.location) <= radius }
}

fun closestCircularArea(children: List<Child>, count: Int): List<Child> {
    return findCircularArea(children, count, findClosest(KORVATUNTURI, children))
}

fun furthestCircularArea(children: List<Child>, count: Int): List<Child> {
    return findCircularArea(children, count, findFurthest(KORVATUNTURI, children))
}

fun findCircularArea(children: List<Child>, count: Int, center: Child): List<Child> {
    var radius = km(10)
    var area = circularArea(children, center.location, radius)

    while (area.size < count && area.size != children.size) {
        radius += km(10)
        area = circularArea(children, center.location, radius)
    }

    if (area.size > 200) {
        throw Exception("Too large area: ${area.size} with radius: ${radius}")
    }
    return area
}

fun findFurthest(location: Location, children: List<Child>): Child =
        findByDistance(location, children) { current, best -> current > best }

fun findClosest(location: Location, children: List<Child>): Child =
        findByDistance(location, children) { current, best -> current < best }


private fun findByDistance(location: Location, children: List<Child>, comparator: (Double, Double) -> Boolean): Child {
    var bestMatch = children[0]
    var bestDistance = distance(location, bestMatch.location)
    for (child in children) {
        val distance = distance(location, child.location)
        if (comparator(distance, bestDistance)) {
            bestDistance = distance
            bestMatch = child
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
    val roundtrip = route.stops.map { it.location }.toMutableList()
    roundtrip.add(0, KORVATUNTURI)
    roundtrip.add(KORVATUNTURI)
    return pathLength(roundtrip)
}

fun pathLength(locations: List<Location>): Double {
    var length = 0.0
    var current =locations[0]
    for (location in locations.subList(1,locations.size)) {
        length += distance(current, location)
        current = location
    }
    return length
}

fun averageCapacityPercent(solution: List<Route>): Double {
    return solution
            .map {
                (it.stops.map { it.giftWeight }.sum().toDouble()) / SLEIGHT_CAPACITY
            }
            .average()
}

