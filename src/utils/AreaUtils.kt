package utils

import model.Location
import model.SLEIGHT_CAPACITY

fun expandUntilFull(center: Location, setup: List<Location>): List<Location> {

    val locations = setup.toMutableList()
    val area = mutableListOf<Location>()
    var weight = 0
    while (weight < SLEIGHT_CAPACITY && locations.isNotEmpty()) {
        val next = findClosest(center, locations)
        weight += next.weight
        area.add(next)
        locations.remove(next)
    }
    if (weight > SLEIGHT_CAPACITY)
        area.removeAt(area.size - 1)
    return area
}
