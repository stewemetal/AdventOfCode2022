package day03

import readFile

fun main() {

    println(
        readFile("day03_input.txt")
            .run {
                var sum = 0
                for (index in indices step 3) {
                    sum += get(index).toSet().intersect(
                        get(index + 1).toSet()
                    ).intersect(
                        get(index + 2).toSet()
                    ).sumOf {
                        it.value
                    }
                }
                sum
            }
    )
}
