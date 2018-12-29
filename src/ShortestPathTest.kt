import model.Location
import java.io.File

fun main(args: Array<String>) {

    val locations = File("src/FinlandRoute.gps")
            .useLines { it.toList() }
            .map { it.split(";") }
            .map { Location(1, it[0].toDouble(), it[1].toDouble(), 0) }


    println("name,latitude,longitude")
    shortestPath(locations).stops.forEachIndexed {  index, it -> println("$index,${it.lat},${it.lon}") }

}


