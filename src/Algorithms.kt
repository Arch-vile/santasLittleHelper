import model.Child
import model.SLEIGHT_CAPACITY
import utils.KORVATUNTURI_STOP

/**
 * Shortest path going through all points and returning. Returned path starts from first location in list
 */
fun shortestPath(coords: List<Child>): List<Child> {
    val points = coords.map { it.location }

    val linKernighan = LinKernighan(ArrayList(points))
    linKernighan.runAlgorithm()
    val route = linKernighan.tour.map { index -> coords[index] }

    val routeStartIndex = route.indexOf(coords[0])
    val foo = route.subList(routeStartIndex, route.size).toMutableList()
    foo.addAll(route.subList(0, routeStartIndex))

    if (foo.size != coords.size || coords[0] != foo[0]) {
        throw Error("You did a mistake there bud!")
    }
    return foo
}

fun shortestPathFromKorvatunturi(coords: List<Child>): List<Child> {
    val allStops = coords.toMutableList()
    allStops.add(0, KORVATUNTURI_STOP)
    val route = shortestPath(allStops).toMutableList()
    route.removeAt(0)
    return route
}


fun binPacking(area: List<Child>): List<Sleight> {
    val start = System.currentTimeMillis()
    val sleights = FFDPacking(area, SLEIGHT_CAPACITY).packSleights()
//    println("[DONE ${System.currentTimeMillis() - start}ms] Bin packing ${area.size} places to ${sleights.size} sleights")
//    sleights.forEachIndexed { index, sleight -> println("Sleigh weight ${SLEIGHT_CAPACITY - sleight.capacity} ${1.0 * (SLEIGHT_CAPACITY - sleight.capacity) / SLEIGHT_CAPACITY} %") }
    return sleights
}