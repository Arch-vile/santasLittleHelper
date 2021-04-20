package solutions

import model.Location
import model.Route
import model.SLEIGHT_CAPACITY
import utils.*

/**
 * Selects an area that fits to sleight and does optimized route for that area
 */

class AreaSelection(setup: List<Location>, val targetFill: Double, val pickTolerance: Double) {
    val locations = setup.toMutableList()

    fun solve(): List<Route> {

        val routes: MutableList<Route> = mutableListOf()

        /**
         * Special logic for south pole. Fetch it first and then go to Cape Town
         */
//        val stops = mutableListOf<Location>()
//        val southPole = getById(locations, 8826)
//        stops.add(southPole)
//        stops.addAll(expandUntilFilled(CAPETOWN, locations, 98, southPole.weight))
//        routes.add(shortestRouteFromKorvatunturi(stops))
//        locations.removeAll(stops)

        while (locations.isNotEmpty()) {
            println("Pending locations ${locations.size}")

            // First fill the sleight mostly from the current location
            val center = findFurthest(KORVATUNTURI, locations)
            val area = expandUntilFilled(center, locations, targetFill, 0).toMutableList()
            locations.removeAll(area)

            // Then fill remaining while heading home
            locations.sortBy {
                distance(center, it) + distance(KORVATUNTURI, it)
            }
            var weight = sleightWeight(Route(area))
            for (location in locations) {
                // TODO: Calculating distance multiple times here
                // Lets stop adding when we get too far of the return path
                if (distance(center, location) + distance(KORVATUNTURI, location) > distance(center, KORVATUNTURI) * pickTolerance) {
                    break
                }

                if(weight + location.weight < SLEIGHT_CAPACITY) {
                    area.add(location)
                    weight += location.weight
                }
            }


            val route = shortestRouteFromKorvatunturiLooping(area)
            routes.add(route)
        }



        return routes
    }


}
