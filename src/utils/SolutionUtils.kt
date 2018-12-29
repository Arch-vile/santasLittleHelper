package utils

import model.Location
import model.Route
import shortestPath

/**
 * Shortest route from Korvatunturi visiting all locations. Route will start and end to Korvatunturi.
 * Given locations must not include Korvatunturi.
 */
fun shortestRouteFromKorvatunturi(locations: List<Location>): Route {

    val stops = locations.toMutableList()
    stops.add(0,KORVATUNTURI)

    // Calculate the shortest route and also add Korvatunturi as last stop
    val routeStops = shortestPath(stops).stops.toMutableList()
    routeStops.add(KORVATUNTURI)

    return Route(routeStops)
}

fun shortestRouteFromKorvatunturiLooping(locations: List<Location>): Route {

    var minRoute = shortestRouteFromKorvatunturi(locations)
    var minLength = Double.MAX_VALUE
    for(i in 1..1000) {
        val route = shortestRouteFromKorvatunturi(locations.shuffled())
        val routeLength = routeLength(route)
        if(routeLength < minLength) {
            minRoute = route
            minLength = routeLength
        }
    }

    return minRoute

}

fun routeFromKorvatunturi(locations: List<Location>): Route {
    val stops = locations.toMutableList()
    stops.add(0,KORVATUNTURI)
    stops.add(KORVATUNTURI)
    return Route(stops)
}

fun sleightWeight(route: Route): Int {
    return route.stops.map { it.weight }.fold(0) { acc, v -> acc + v }
}
