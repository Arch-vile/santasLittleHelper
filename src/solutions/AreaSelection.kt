package solutions

import model.Child
import model.Route
import shortestPathFromKorvatunturi
import utils.KORVATUNTURI
import utils.expandUntilFull
import utils.findClosest

/**
 * Selects an area that fits to sleight and does optimized route for that area
 */

class AreaSelection(setup: List<Child>) {
    val children = setup.toMutableList()

    fun solve(): List<Route> {

        val routes: MutableList<Route> = mutableListOf()
        while (children.isNotEmpty()) {
            println(children.size)
            val center = findClosest(KORVATUNTURI, children)
            val area = expandUntilFull(center.location, children)
            val route = shortestPathFromKorvatunturi(area)
            children.removeAll(route)
            routes.add(Route(route))
        }

        return routes
    }


}
