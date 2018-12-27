package utils

fun formatInt(d: Double): String {
    return String.format("%.0f",d).padStart(15,'0').toString()
}