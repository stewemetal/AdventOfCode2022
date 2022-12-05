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
