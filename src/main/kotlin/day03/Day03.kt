package day03

import readFile

fun main() {

    println(
        readFile("day03_input.txt")
            .map { rucksack ->
                rucksack.take((rucksack.length / 2f).toInt()).toSet().intersect(
                    rucksack.takeLast((rucksack.length / 2f).toInt()).toSet()
                )
            }.sumOf { intersectionOfRucksack ->
                intersectionOfRucksack.sumOf { it.value }
            }
    )
}

val Char.value
    get() =
        when (this) {
            in 'a'..'z' -> this.code - ('a'.code) + 1
            in 'A'..'Z' -> this.code - ('A'.code) + 27
            else -> 0
        }