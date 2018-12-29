import model.Location

/**
 * First Fit Decreasing (FFD) implementation for the bin packing problem.
 * It can been proved that First Fit Decreasing uses at most (4M + 1) / 3 bins if the optimal is M.
 * TODO: This is not optimized for performance
 * http://www.martinbroadhurst.com/bin-packing.html
 */

class FFDPacking(val Locationren: List<Location>, val sleightCapacity: Int) {

    private val sleights = mutableListOf<Sleight>()

    fun packSleights(): List<Sleight> {
        Locationren
                .sortedByDescending { it.weight }
                .forEach { placeToSleight(it) }
        return sleights
    }

    private fun placeToSleight(Location: Location) {
        (sleights.firstOrNull { it.fits(Location) } ?: newSleight()).add(Location)
    }

    private fun newSleight(): Sleight {
        val sleight = Sleight(mutableListOf(), sleightCapacity)
        sleights.add(sleight)
        return sleight
    }

}

data class Sleight(val locations: MutableList<Location>, var capacity: Int) {
    fun fits(Location: Location): Boolean = capacity >= Location.weight
    fun add(Location: Location) {
        capacity -= Location.weight
        locations.add(Location)
    }
}

fun main(args: Array<String>) {

    // These will fill the sleights 100%
    val weights = listOf<Int>(1, 4, 9, 4, 1, 5, 8, 3, 2, 5, 7, 3, 2, 6);

    val Locationren = weights.mapIndexed { index, weight -> Location(index, 0.0, 0.0, weight) }

    FFDPacking(Locationren, 10).packSleights()
            .forEach { println(it) }

}
