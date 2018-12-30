import model.Location
import model.Route
import utils.*
import java.io.File

fun test1(args: Array<String>) {

    val locations = File("src/FinlandRoute.gps")
            .useLines { it.toList() }
            .map { it.split(";") }
            .map { Location(1, it[0].toDouble(), it[1].toDouble(), 0) }


    println("name,latitude,longitude")
    shortestPath(locations).stops.forEachIndexed {  index, it -> println("$index,${it.lat},${it.lon}") }

}

fun main(args: Array<String>) {

    val suffled = listOf(
            Location(63, -10.743645, 14.99626, 1),//
            Location(344, -9.417851, 18.438644, 1),//
            Location(396, -14.918173, 13.525616, 1),//
            Location(1000, -15.184003, 13.723428, 1),//
            Location(2453, -11.325415, 16.164647, 1),//
            Location(2064, -9.302792, 14.895628, 1),//
            Location(2258, -15.774895, 20.344059, 1),//
            Location(2908, -10.013689, 14.9022, 1),//
            Location(4908, -12.6109, 13.417986, 1),//
            Location(4501, -12.377854, 16.940497, 1),//
            Location(4488, -9.266798, 14.921015, 1),//
            Location(5589, -10.004859, 14.92643, 1),//
            Location(5667, -14.652108, 17.695263, 1),//
            Location(6984, -14.934674, 13.511731, 1),//
            Location(7448, -9.686262, 14.427555, 1),//
            Location(7208, -12.352252, 13.521963, 1),//
            Location(7564, -14.93276, 13.47544, 1),//
            Location(8902, -15.828, 20.370843, 1),//
            Location(9353, -15.789733, 20.3494, 1),//
            Location(2674, -12.757164, 15.798326, 1),//
            Location(6704, -11.360374, 16.197339, 1)
    )

    val foo = listOf(
            KORVATUNTURI,
            Location(4488, -9.266798, 14.921015, 1),//
            Location(2064, -9.302792, 14.895628, 1),//
            Location(7448, -9.686262, 14.427555, 1),//
            Location(2908, -10.013689, 14.9022, 1),//
            Location(5589, -10.004859, 14.92643, 1),//
            Location(63, -10.743645, 14.99626, 1),//

            Location(7208, -12.352252, 13.521963, 1),//
            Location(4908, -12.6109, 13.417986, 1),//
            Location(7564, -14.93276, 13.47544, 1),//
            Location(6984, -14.934674, 13.511731, 1),//
            Location(396, -14.918173, 13.525616, 1),//
            Location(1000, -15.184003, 13.723428, 1),//


            Location(5667, -14.652108, 17.695263, 1),//
            Location(8902, -15.828, 20.370843, 1),//
            Location(9353, -15.789733, 20.3494, 1),//
            Location(2258, -15.774895, 20.344059, 1),//
            Location(4501, -12.377854, 16.940497, 1),//
            Location(2674, -12.757164, 15.798326, 1),//

            Location(2453, -11.325415, 16.164647, 1),//
            Location(6704, -11.360374, 16.197339, 1),//
            Location(344, -9.417851, 18.438644, 1),//
            KORVATUNTURI
    )


    val manual = Route(foo)

    println(distanceForHumans(routeLength(shortestRouteFromKorvatunturiLooping(suffled))))
    println(distanceForHumans(routeLength(manual)))

}


