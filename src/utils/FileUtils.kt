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
    solution.map { it.stops.joinToString(separator = "; ") { it.id.toString() } }.forEach { out.appendText(it + "\n") }
}