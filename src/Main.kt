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


}








