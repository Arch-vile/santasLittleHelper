package utils

import model.Child
import java.io.File

fun exportForGpsVizualizer(locations: List<Child>) {
    val file = File("resources/gpsVisualizerExport.log")
    file.writeText("name,latitude,longitude\n")
    locations.forEach { file.appendText("${it.id},${it.location.lat},${it.location.lon}\n") }
}

fun exportForGpsVizualizerTrack(locations: List<Child>) {
    val file = File("resources/gpsVisualizerExport.log")
    file.writeText("latitude,longitude\n")
    val route = locations.toMutableList()
    route.add(0, Child(-1, KORVATUNTURI, 0))
    route.add(Child(-2, KORVATUNTURI, 0))
    route.forEach { file.appendText("${it.location.lat},${it.location.lon}\n") }
}
