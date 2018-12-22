import model.Child
import model.Location

/**
 * First Fit Decreasing (FFD) implementation for the bin packing problem.
 * It can been proved that First Fit Decreasing uses at most (4M + 1) / 3 bins if the optimal is M.
 * TODO: This is not optimized for performance
 * http://www.martinbroadhurst.com/bin-packing.html
 */

class FFDPacking(val children: List<Child>, val sleightCapacity: Int) {

    private val sleights = mutableListOf<Sleight>()

    fun packSleights(): List<Sleight> {
        children
                .sortedByDescending { it.giftWeight }
                .forEach { placeToSleight(it) }
        return sleights
    }

    private fun placeToSleight(child: Child) {
        (sleights.firstOrNull { it.fits(child) } ?: newSleight()).add(child)
    }

    private fun newSleight(): Sleight {
        val sleight = Sleight(mutableListOf(), sleightCapacity)
        sleights.add(sleight)
        return sleight
    }

}

data class Sleight(val children: MutableList<Child>, var capacity: Int) {
    fun fits(child: Child): Boolean = capacity >= child.giftWeight
    fun add(child: Child) {
        capacity -= child.giftWeight
        children.add(child)
    }
}

fun main(args: Array<String>) {

    // These will fill the sleights 100%
    val weights = listOf<Int>(1, 4, 9, 4, 1, 5, 8, 3, 2, 5, 7, 3, 2, 6);

    val children = weights.mapIndexed { index, weight -> Child(index, Location(0.0,0.0), weight)  }

    FFDPacking(children, 10).packSleights()
            .forEach { println(it) }

}
