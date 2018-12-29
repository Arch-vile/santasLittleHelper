import model.Child
import model.Location
import model.Route
import solutions.AreaSelection
import solutions.BinPacking
import solutions.ClosestLocationStandard
import utils.*
import java.time.LocalDateTime

fun main2(args: Array<String>) {


    for (areaSize in 5..100 step 5) {

        val children = readInput("resources/nicelist.txt").toMutableList()
        val startCount = children.size
        val target = 8135970987

        var routes = emptyList<Route>().toMutableList()
        while (children.size > 0) {
            val nextArea = closestCircularArea(children, areaSize)

            // Do the binpacking and select the max sleight
            val solution = BinPacking(nextArea).maxedSleight()


            children.removeAll(solution.stops)
            routes.add(solution)
//            val length = routeLength(routes)
//            println("${children.size}\t${String.format("%f", length)}\t${(1.0 * startCount - children.size) / startCount}\t${(length * 1.0) / target}")
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
            KORVATUNTURI_STOP,
            Child(4488,Location(lat = -9.266798, lon = 14.921015), 1),//
            Child(2064,Location(lat = -9.302792, lon = 14.895628), 1),//
            Child(7448,Location(lat = -9.686262, lon = 14.427555), 1),//
            Child(2908,Location(lat = -10.013689, lon = 14.9022), 1),//
            Child(5589,Location(lat = -10.004859, lon = 14.92643), 1),//
            Child(63,Location(lat = -10.743645, lon = 14.99626), 1),//

            Child(7208,Location(lat = -12.352252, lon = 13.521963), 1),//
            Child(4908,Location(lat = -12.6109, lon = 13.417986), 1),//
            Child(7564,Location(lat = -14.93276, lon = 13.47544), 1),//
            Child(6984,Location(lat = -14.934674, lon = 13.511731), 1),//
            Child(396,Location(lat = -14.918173, lon = 13.525616), 1),//
            Child(1000,Location(lat = -15.184003, lon = 13.723428), 1),//


            Child(5667,Location(lat = -14.652108, lon = 17.695263), 1),//
            Child(8902,Location(lat = -15.828, lon = 20.370843), 1),//
            Child(9353,Location(lat = -15.789733, lon = 20.3494), 1),//
            Child(2258,Location(lat = -15.774895, lon = 20.344059), 1),//
            Child(4501,Location(lat = -12.377854, lon = 16.940497), 1),//
            Child(2674,Location(lat = -12.757164, lon = 15.798326), 1),//

            Child(2453,Location(lat = -11.325415, lon = 16.164647), 1),//
            Child(6704,Location(lat = -11.360374, lon = 16.197339), 1),//
            Child(344,Location(lat = -9.417851, lon = 18.438644), 1),//
            KORVATUNTURI_STOP
    )


    shortestPath(foo).forEach { println("${it.id}") }

     println(forHumans(routeLength(Rout(foo))))
}








