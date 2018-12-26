import model.Route
import solutions.BinPacking
import solutions.ClosestLocationStandard
import utils.*

fun main(args: Array<String>) {


    val children = readInput("resources/nicelist.txt").toMutableList()
    val startCount = children.size
    val target = 8135970987

    var routes = emptyList<Route>().toMutableList()
    while (children.size > 0) {
        val nextArea = furthestCircularArea(children, 30)

//        get the best bin!!! loop with different counts

        val solution = BinPacking(nextArea).solve()[0]
        children.removeAll(solution.stops)
        routes.add(solution)
        val length = routeLength(routes)
        println("${children.size}\t${String.format("%f", length)}\t${ (1.0*startCount-children.size)/startCount }\t${(length*1.0)/target }")
    }

    println(String.format("%f", routeLength(routes)))

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







