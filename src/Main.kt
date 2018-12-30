import model.SLEIGHT_CAPACITY
import solutions.AreaSelection
import utils.*


fun main2(args: Array<String>) {
    val solution = deserialize()
    solution.sortedBy { routeLength(it) }.reversed()
            .take(50).takeLast(10)
            .forEachIndexed { index, route -> exportForGpsVizualizerTrack(index.toString(), route.stops) }

}

fun main3(args: Array<String>) {

    println("Hello, Santa!")
    val input = readInput("resources/nicelist.txt")
    val solution = AreaSelection(input).solve()

    println("Route length: ${forHumans(routeLength(solution))}")
    serialize(solution)
    writeOutput(solution)
}

fun main(args: Array<String>) {
    println("Hello, Santa!")
    val range = km(1500)
    val locations = readInput("resources/nicelist.txt")
    val area = circularArea(locations, SOUTHAFRICA, range).toMutableList()
    area.addAll(circularArea(locations, WARSAW, km(500)))

    //val solution1 = ClosestLocationStandard(input).solve()
    //val solution2 = BinPacking(input).solve()
    val solution3 = AreaSelection(area).solve()

    println("areaBuilding\tSA and Warsaw\t${area.size}\t" +
            "${solution3.size}\t${forHumans(routeLength(solution3))}\t${averageCapacityPercent(solution3)}\n")
    exportForGpsVizualizer(area)

    exportForGpsVizualizerTrack(solution3)


}










