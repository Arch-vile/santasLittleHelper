import model.Route
import solutions.BinPacking
import solutions.ClosestLocationStandard
import utils.*
import java.time.LocalDateTime

fun main(args: Array<String>) {

    for (areaSize in 5..100 step 5) {

        val children = readInput("resources/nicelist.txt").toMutableList()
        val startCount = children.size
        val target = 8135970987

        var routes = emptyList<Route>().toMutableList()
        while (children.size > 0) {
            val nextArea = furthestCircularArea(children, areaSize)

            // Do the binpacking and select the max sleight
            val solution = BinPacking(nextArea).maxedSleight()


            children.removeAll(solution.stops)
            routes.add(solution)
//            val length = routeLength(routes)
//            println("${children.size}\t${String.format("%f", length)}\t${(1.0 * startCount - children.size) / startCount}\t${(length * 1.0) / target}")
        }

        println(LocalDateTime.now().toString() +
                " area size ${areaSize.toString().padStart(5)}" +
                " route length " + formatInt(routeLength(routes)))
    }
}

fun compare(args: Array<String>) {
    println("Hello, Santa!")


    for (range in 100 until km(5000) step km(50)) {
        val input = circularArea(readInput("resources/nicelist.txt"), SOUTHAFRICA, range)
        val solution1 = ClosestLocationStandard(input).solve()
        val solution2 = BinPacking(input).solve()

        println("$range\t${input.size}\t" +
                "closest\t${solution1.size}\t${routeLength(solution1)}\t${averageCapacityPercent(solution1)}\t" +
                "binPack\t${solution2.size}\t${routeLength(solution2)}\t${averageCapacityPercent(solution2)}\t")

//        exportForGpsVizualizerTrack(solution[0].stops)
    }
}







