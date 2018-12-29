import model.Location
import model.Route
import model.SLEIGHT_CAPACITY

/**
 * Shortest path visiting all locations and returning. Returned route starts from first location in list
 */
fun shortestPath(points: List<Location>): Route {
    val linKernighan = LinKernighan(ArrayList(points))
    linKernighan.runAlgorithm()
    val route = linKernighan.tour.map { index -> points[index] }

    val routeStartIndex = route.indexOf(points[0])
    val foo = route.subList(routeStartIndex, route.size).toMutableList()
    foo.addAll(route.subList(0, routeStartIndex))

    if (foo.size != points.size || points[0] != foo[0]) {
        throw Error("You did a mistake there bud!")
    }
    return Route(foo)
}


fun binPacking(area: List<Location>): List<Sleight> {
    val sleights = FFDPacking(area, SLEIGHT_CAPACITY).packSleights()
    return sleights
}