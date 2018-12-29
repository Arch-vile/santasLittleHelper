package utils

import model.Child
import model.Location
import model.SLEIGHT_CAPACITY

fun expandUntilFull(center: Location, setup: List<Child>): List<Child> {

    val children = setup.toMutableList()
    val area = mutableListOf<Child>()
    var weight = 0
    while (weight < SLEIGHT_CAPACITY && children.isNotEmpty()) {
        val next = findClosest(center, children)
        weight += next.giftWeight
        area.add(next)
        children.remove(next)
    }
    if (weight > SLEIGHT_CAPACITY)
        area.removeAt(area.size - 1)
    return area
}
