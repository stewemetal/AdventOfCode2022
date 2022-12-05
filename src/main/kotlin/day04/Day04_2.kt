package day04

import readFile

fun main() {

    println(
        readFile("day04_input.txt")
            .run {
                var count = 0
                forEach { pairing ->
                    val (range1, range2) = pairing.split(",")
                        .map { rangeDefinition ->
                            val (first, last) = rangeDefinition.split("-").map { it.toInt() }
                            first..last
                        }
                    if (range1.intersect(range2).isNotEmpty()) {
                        count++
                    }
                }
                count
            }
    )
}
