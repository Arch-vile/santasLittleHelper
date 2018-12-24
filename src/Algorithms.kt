import model.Child
import model.Location
import model.SLEIGHT_CAPACITY

fun shortestPath(coords: List<Child>): List<Child> {
    val points = coords.map { it.location }

    val linKernighan = LinKernighan(ArrayList(points))
    linKernighan.runAlgorithm()
    return linKernighan.tour.map { index -> coords[index] }
}

fun binPacking(area: List<Child>): List<Sleight> {
    val start = System.currentTimeMillis()
    val sleights = FFDPacking(area, SLEIGHT_CAPACITY).packSleights()
//    println("[DONE ${System.currentTimeMillis() - start}ms] Bin packing ${area.size} places to ${sleights.size} sleights")
//    sleights.forEachIndexed { index, sleight -> println("Sleigh weight ${SLEIGHT_CAPACITY - sleight.capacity} ${1.0 * (SLEIGHT_CAPACITY - sleight.capacity) / SLEIGHT_CAPACITY} %") }
    return sleights
}