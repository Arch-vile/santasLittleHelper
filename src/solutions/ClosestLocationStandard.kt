package solutions

import model.Location
import model.Route
import model.SLEIGHT_CAPACITY
import utils.KORVATUNTURI
import utils.distance


class ClosestLocationStandard(setup: List<Location>) {
    var currentLocation = KORVATUNTURI
    val locations = setup.toMutableList()
    val routes: MutableList<Route> = mutableListOf()


    fun solve(): List<Route> {
        while (locations.size > 0) {
            routes.add(scoutRoute())
        }
        return routes
    }

    private fun findNearest(): Location {

        var nearest = locations[0]
        var nearestDistance = Double.MAX_VALUE
        for (location in locations) {
            val distance = distance(currentLocation, location)
            if (distance < nearestDistance) {
                nearestDistance = distance
                nearest = location
            }
        }

        return nearest
    }

    private fun scoutRoute(): Route {
        val route: MutableList<Location> = mutableListOf()
        var sleightWeight = 0

        while (sleightWeight < SLEIGHT_CAPACITY && locations.isNotEmpty()) {
            val next = findNearest()
            sleightWeight += next.weight

            if (sleightWeight < SLEIGHT_CAPACITY) {
                currentLocation = next
                route.add(next)
                locations.remove(next)
            }
        }

        currentLocation = KORVATUNTURI
        return Route(route.toList())
    }

}

