package model

val SLEIGHT_CAPACITY = 10000000

data class Location(val id: Int, val lat: Double, val lon: Double, val weight: Int)
data class Route(val stops: List<Location>)
