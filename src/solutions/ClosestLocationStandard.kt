package solutions

import model.Child
import model.Route
import model.SLEIGHT_CAPACITY
import utils.KORVATUNTURI
import utils.distance


class ClosestLocationStandard(setup: List<Child>) {
    var currentLocation = KORVATUNTURI
    val children = setup.toMutableList()
    val routes: MutableList<Route> = mutableListOf()


    fun solve(): List<Route> {
        while (children.size > 0) {
            routes.add(scoutRoute())
        }
        return routes
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

    private fun scoutRoute(): Route {
        val route: MutableList<Child> = mutableListOf()
        var sleightWeight = 0

        while (sleightWeight < SLEIGHT_CAPACITY && children.isNotEmpty()) {
            val next = findNearest()
            sleightWeight += next.giftWeight

            if (sleightWeight < SLEIGHT_CAPACITY) {
                currentLocation = next.location
                route.add(next)
                children.remove(next)
            }
        }

        currentLocation = KORVATUNTURI
//        println("Scouted for route with ${route.size} stops")
//        println("${children.size} stops remaining")
        return Route(route.toList())
    }

}

