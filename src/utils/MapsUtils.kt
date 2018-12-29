package utils

import model.Location
import java.io.File

fun exportForGpsVizualizer(locations: List<Location>) {
    val file = File("resources/gpsVisualizerExport_places.log")
    file.writeText("name,latitude,longitude\n")
    locations.forEach { file.appendText("${it.id},${it.lat},${it.lon}\n") }
}


fun exportForGpsVizualizerTrack(suffix: String, locations: List<Location>) {
    val file = File("resources/gpsVisualizerExport_${suffix}.log")
    file.writeText("latitude,longitude\n")
    val route = locations.toMutableList()
    route.add(0, KORVATUNTURI)
    route.add(KORVATUNTURI)
    route.forEach { file.appendText("${it.lat},${it.lon}\n") }
}
