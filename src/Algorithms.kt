import model.Child
import model.Location
import model.SLEIGHT_CAPACITY

fun shortestPath(coords: List<Location>): List<Location> {
    val points = coords.map { Point(it.lat, it.lon) }

    val linKernighan = LinKernighan(ArrayList(points))
    linKernighan.runAlgorithm()
    throw Exception("Algorithm need to handle gps distance. Otherwise points on 180 degrees and 0 will be considered far away")
    return linKernighan.tour.map { index -> coords[index] }
}

fun binPacking(area: List<Child>): List<Sleight> {
    val start = System.currentTimeMillis()
    val sleights = FFDPacking(area, SLEIGHT_CAPACITY).packSleights()
//    println("[DONE ${System.currentTimeMillis() - start}ms] Bin packing ${area.size} places to ${sleights.size} sleights")
//    sleights.forEachIndexed { index, sleight -> println("Sleigh weight ${SLEIGHT_CAPACITY - sleight.capacity} ${1.0 * (SLEIGHT_CAPACITY - sleight.capacity) / SLEIGHT_CAPACITY} %") }
    return sleights
}