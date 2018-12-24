import model.Child
import model.Location
import java.io.File

fun main(args: Array<String>) {

    val locations = File("src/FinlandRoute.gps")
            .useLines { it.toList() }
            .map { it.split(";") }
            .map { Location(it[0].toDouble(), it[1].toDouble()) }
            .map { Child(1,it,0) }


    println("name,latitude,longitude")
    shortestPath(locations).forEachIndexed {  index, it -> println("$index,${it.location.lat},${it.location.lon}") }

}


