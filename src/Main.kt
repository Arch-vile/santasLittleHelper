import solutions.BinPacking
import solutions.ClosestLocationStandard
import utils.*


fun main(args: Array<String>) {
    println("Hello, Santa!")


    for (range in 100 until km(5000) step km(50)) {
        val input = circularArea(readInput("resources/nicelist.txt"), SOUTHAFRICA, range)
        val solution1 = ClosestLocationStandard(input).solve()
        val solution2 = BinPacking(input).solve()

        println("$range\t${input.size}\t" +
                "closest\t${solution1.size}\t${routeLength(solution1)}\t" +
                "binPack\t${solution2.size}\t${routeLength(solution2)}\t")

//        exportForGpsVizualizerTrack(solution[0].stops)
    }
}







