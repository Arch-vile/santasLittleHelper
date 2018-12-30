package solutions

import model.Location
import model.Route
import utils.*

/**
 * Selects an area that fits to sleight and does optimized route for that area
 */

class AreaSelection(setup: List<Location>) {
    val locations = setup.toMutableList()

    fun solve(): List<Route> {

        val routes: MutableList<Route> = mutableListOf()

        /**
         * Special logic for south pole. Fetch it first and then go to Cape Town
         */
        val stops = mutableListOf<Location>()
        val southPole = getById(locations, 8826)
        stops.add(southPole)
        stops.addAll(expandUntilFilled(CAPETOWN, locations, 98, southPole.weight))
        routes.add(shortestRouteFromKorvatunturi(stops))
        locations.removeAll(stops)

        while (locations.isNotEmpty()) {
            println("Pending locations ${locations.size}")
            val center = findFurthest(KORVATUNTURI, locations)
            val area = expandUntilFilled(center, locations, 98, 0)

            val route = shortestRouteFromKorvatunturiLooping(area)
            locations.removeAll(route.stops)
            routes.add(route)
        }

        return routes
    }


}
