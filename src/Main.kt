import solutions.AreaSelection
import utils.*


fun main2(args: Array<String>) {
    val solution = deserialize()
    solution.sortedBy { routeLength(it) }.reversed()
            .take(50).takeLast(10)
            .forEachIndexed { index, route -> exportForGpsVizualizerTrack(index.toString(), route.stops) }

}

fun main(args: Array<String>) {

    println("Hello, Santa!")
    val input = readInput("resources/nicelist.txt")
    val solution = AreaSelection(input, 99.0, 1.20).solve()

    println("Route length: ${distanceForHumans(routeLength(solution))}")
    serialize(solution)
    writeOutput(solution)
}

fun maini(args: Array<String>) {
    println("Hello, Santa!")
    val range = km(1500)
    val locations = readInput("resources/nicelist.txt")
    val area = circularArea(locations, SOUTHAFRICA, range).toMutableList()
    area.addAll(circularArea(locations, WARSAW, km(500)))


    for(i in 0..100) {

        val targetFill = 50 + Math.random()*50
        val tolerance =  1.0 + Math.random()*0.3

        val solution3 = AreaSelection(area, targetFill, tolerance).solve()

        println("areaBuilding\tSA and Warsaw\t${doubleForHumans(targetFill)}\t${doubleForHumans(tolerance)}\t${area.size}\t" +
                "${solution3.size}\t${distanceForHumans(routeLength(solution3))}\t${averageCapacityPercent(solution3)}\n")
//    exportForGpsVizualizer(area)
//
//    exportForGpsVizualizerTrack(solution3)

    }
}










