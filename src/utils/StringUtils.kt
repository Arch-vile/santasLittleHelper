package utils

import java.text.DecimalFormat

var formatter = DecimalFormat("###,###")
var doubleF = DecimalFormat("000.##")

fun distanceForHumans(d: Double): String {
    return formatter.format(d)
}

fun doubleForHumans(d: Double): String {
   return doubleF.format(d)
}