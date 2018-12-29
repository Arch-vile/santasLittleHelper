package solutions

import binPacking
import model.Location
import model.Route
import utils.shortestRouteFromKorvatunturi

class BinPacking(setup: List<Location>) {

    private val locations = setup

    fun solve(): List<Route> {
        return binPacking(locations)
                .map { it.locations }
                .map { shortestRouteFromKorvatunturi(it) }
    }

    fun maxedSleight(): Route {
        return Route(binPacking(locations)
                .minBy { it.capacity }!!.locations)
    }

}