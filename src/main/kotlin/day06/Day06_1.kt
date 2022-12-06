package day06

import readFileForDay

fun main() {
    val lengthOfMarker = 4
    println(
        readFileForDay(6)[0]
            .windowed(lengthOfMarker)
            .indexOfFirst {
                it.toSet().size == lengthOfMarker
            } + lengthOfMarker
    )
}