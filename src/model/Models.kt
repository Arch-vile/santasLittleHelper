package model

val SLEIGHT_CAPACITY = 10000000

data class Location(val lat: Double, val lon: Double)

data class Child(val id: Int, val location: Location, val giftWeight: Int)

data class Route(val stops: List<Child>)
