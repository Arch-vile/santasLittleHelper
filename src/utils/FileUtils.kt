package utils

import model.Location
import model.Route
import java.io.File


fun readInput(filename: String): List<Location> =
        File(filename).useLines { it.toList() }
                .map { it.split(";") }
                .map { Location(it[0].toInt(), it[1].toDouble(), it[2].toDouble(), it[3].toInt()) }

fun writeOutput(solution: List<Route>) {
    val out = File("resources/output.csv")
    out.writeText("")
    solution
            .map {
                val copy = it.stops.toMutableList()
                copy.remove(KORVATUNTURI)
                copy
            }
            .map { it.joinToString(separator = "; ") { it.id.toString() } }.forEach { out.appendText(it + "\n") }
}

fun serialize(solution: List<Route>) {
    val out = File("resources/serialized.csv")
    out.writeText("")
    solution.forEach {
        it.stops.forEach {
            out.appendText("${it.id},${it.lat},${it.lon},${it.weight}|")
        }
        out.appendText("\n")
    }
}


fun deserialize(): List<Route> {
    return File("resources/serialized.csv").useLines { it.toList() }
            .map {
                it.split("|")
                        .filter { it.isNotEmpty() }
                        .map { it.split(",") }
                        .map { Location(it[0].toInt(),it[1].toDouble(),it[2].toDouble(),it[3].toInt()) }
            }
            .map { Route(it)}
}
