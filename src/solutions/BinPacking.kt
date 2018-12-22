package solutions

import binPacking
import model.Child
import model.Route

class BinPacking(setup: List<Child>) {

    val children = setup

    fun solve(): List<Route> {
        return binPacking(children).map { Route(it.children) }
    }

}