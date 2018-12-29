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