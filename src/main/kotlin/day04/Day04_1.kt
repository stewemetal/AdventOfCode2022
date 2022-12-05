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
                    if ((range1.first <= range2.first && range1.last >= range2.last)
                        || (range2.first <= range1.first && range2.last >= range1.last)
                    ) {
                        count++
                    }
                }
                count
            }
    )
}
