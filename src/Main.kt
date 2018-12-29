import model.Location
import model.Route
import solutions.AreaSelection
import solutions.BinPacking
import solutions.ClosestLocationStandard
import utils.*
import java.time.LocalDateTime

fun main2(args: Array<String>) {


    for (areaSize in 5..100 step 5) {

        val Locationren = readInput("resources/nicelist.txt").toMutableList()
        val startCount = Locationren.size
        val target = 8135970987

        var routes = emptyList<Route>().toMutableList()
        while (Locationren.size > 0) {
            val nextArea = closestCircularArea(Locationren, areaSize)

            // Do the binpacking and select the max sleight
            val solution = BinPacking(nextArea).maxedSleight()


            Locationren.removeAll(solution.stops)
            routes.add(solution)
//            val length = routeLength(routes)
//            println("${locations.size}\t${String.format("%f", length)}\t${(1.0 * startCount - locations.size) / startCount}\t${(length * 1.0) / target}")
        }

        println(LocalDateTime.now().toString() +
                " area size ${areaSize.toString().padStart(5)}" +
                " route length " + forHumans(routeLength(routes)))
    }
}

fun main(args: Array<String>) {
    println("Hello, Santa!")
    val range = km(1500)
    val input = circularArea(readInput("resources/nicelist.txt"), SOUTHAFRICA, range)
    val solution1 = ClosestLocationStandard(input).solve()
    val solution2 = BinPacking(input).solve()
    val solution3 = AreaSelection(input).solve()

    println("$range\t${input.size}\n" +
            "closest     \t${solution1.size}\t${forHumans(routeLength(solution1))}\t${averageCapacityPercent(solution1)}\n" +
            "binPack     \t${solution2.size}\t${forHumans(routeLength(solution2))}\t${averageCapacityPercent(solution2)}\n" +
            "areaBuilding\t${solution3.size}\t${forHumans(routeLength(solution3))}\t${averageCapacityPercent(solution3)}\n")
    exportForGpsVizualizerTrack("0", solution3[0].stops)
    exportForGpsVizualizerTrack("1", solution3[1].stops)
    exportForGpsVizualizerTrack("2", solution3[2].stops)
    exportForGpsVizualizerTrack("3", solution3[3].stops)
    exportForGpsVizualizer(input)


    val foo = listOf(
            KORVATUNTURI,
            Location(4488, -9.266798, 14.921015, 1),//
            Location(2064, -9.302792, 14.895628, 1),//
            Location(7448, -9.686262, 14.427555, 1),//
            Location(2908, -10.013689, 14.9022, 1),//
            Location(5589, -10.004859, 14.92643, 1),//
            Location(63, -10.743645, 14.99626, 1),//

            Location(7208, -12.352252, 13.521963, 1),//
            Location(4908, -12.6109, 13.417986, 1),//
            Location(7564, -14.93276, 13.47544, 1),//
            Location(6984, -14.934674, 13.511731, 1),//
            Location(396, -14.918173, 13.525616, 1),//
            Location(1000, -15.184003, 13.723428, 1),//


            Location(5667, -14.652108, 17.695263, 1),//
            Location(8902, -15.828, 20.370843, 1),//
            Location(9353, -15.789733, 20.3494, 1),//
            Location(2258, -15.774895, 20.344059, 1),//
            Location(4501, -12.377854, 16.940497, 1),//
            Location(2674, -12.757164, 15.798326, 1),//

            Location(2453, -11.325415, 16.164647, 1),//
            Location(6704, -11.360374, 16.197339, 1),//
            Location(344, -9.417851, 18.438644, 1),//
            KORVATUNTURI
    )


    shortestPath(foo).stops.forEach { println("${it.id}") }

    println(forHumans(routeLength(Route(foo))))
}








