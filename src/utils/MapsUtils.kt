package utils

import model.Location
import model.Route
import java.io.File

fun exportForGpsVizualizerTrack(solution: List<Route>) {

    val routesToPlot = solution.toMutableList()

    while(routesToPlot.size > 8) {

        val first = routesToPlot.first()
        val last = routesToPlot.last()
        val allStops = first.stops.toMutableList()
        allStops.addAll(last.stops)
        val combined = Route(allStops)
        routesToPlot.remove(first)
        routesToPlot.remove(last)
        routesToPlot.add(routesToPlot.size/2, combined)
    }

    routesToPlot.forEachIndexed { index, route ->
        exportForGpsVizualizerTrack(""+index, route.stops)
    }

    println("Created ${routesToPlot.size} files for visualization")
}

fun exportForGpsVizualizer(locations: List<Location>) {
    val file = File("resources/gpsVisualizerExport_places.log")
    file.writeText("name,latitude,longitude\n")
    locations.forEach { file.appendText("${it.id},${it.lat},${it.lon}\n") }
}


fun exportForGpsVizualizerTrack(suffix: String, locations: List<Location>) {
    val file = File("resources/gpsVisualizerExport_${suffix}.log")
    file.writeText("latitude,longitude\n")
    val route = locations
            .toMutableList()
            .map {
                // Maps visualization fails for -90.0
                if(it.lat == -90.0)
                    Location(it.id, -89.0, it.lon, it.weight)
                else
                    it
            }
    route.forEach { file.appendText("${it.lat},${it.lon}\n") }
}
