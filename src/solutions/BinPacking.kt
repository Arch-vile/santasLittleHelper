package solutions

import binPacking
import model.Child
import model.Route
import shortestPath

class BinPacking(setup: List<Child>) {

    private val children = setup

    fun solve(): List<Route> {
        return binPacking(children)
                .map { it.children }
                .map { shortestPath(it)}
                .map { Route(it) }
    }

}