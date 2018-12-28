package utils

import java.text.DecimalFormat

var formatter = DecimalFormat("###,###")

fun forHumans(d: Double): String {
    return formatter.format(d)
}