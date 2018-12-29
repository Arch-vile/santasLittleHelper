import model.SLEIGHT_CAPACITY
import solutions.AreaSelection
import solutions.BinPacking
import solutions.ClosestLocationStandard
import utils.*

fun main(args: Array<String>) {

    println("Hello, Santa!")
    val input = readInput("resources/nicelist.txt")
    val solution = AreaSelection(input).solve()
    println("Route length: ${routeLength(solution)}")
    writeOutput(solution)
}

fun main2(args: Array<String>) {
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


    solution3.map { sleightWeight(it) }
            //.map { SLEIGHT_CAPACITY - it }
            .forEach { println(it * 100 / SLEIGHT_CAPACITY) }

}









