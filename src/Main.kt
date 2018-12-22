import solutions.BinPacking
import solutions.ClosestLocationStandard
import utils.*


fun main(args: Array<String>) {
    println("Hello, Santa!")

    val input = circularArea(readInput("resources/nicelist.txt"), SOUTHAFRICA, km(500))
   // exportForGpsVizualizer(input)
//    val solution = ClosestLocationStandard(input).solve()
    val solution = BinPacking(input).solve()
    writeOutput(solution)

    println("Santa, I am ready!")

    println("Route length " + String.format("%f", routeLength(solution)))
    println("Routes total ${solution.size}")

    println(solution[0].stops.size)
    exportForGpsVizualizerTrack(solution[0].stops)
}





