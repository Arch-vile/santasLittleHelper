import java.io.File

fun main(args: Array<String>) {
    println("Hello, Santa!")

    val children = readChildren("src/nicelist.txt")
    children.forEach { println(it.toString()) }


}

fun readChildren(filename: String): List<Child> =
        File(filename).useLines { it.toList() }
                .map { it.split(";") }
                .map { Child(it[0], it[1].toDouble(), it[2].toDouble(), it[3].toInt()) }


data class Child(val id: String, val lat: Double, val lon: Double, val giftWeight: Int)