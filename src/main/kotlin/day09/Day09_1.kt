package day09

import readFile
import readFileForDay
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.math.abs

fun main() {

    fun isTailTouchingHead(head: Coordinates, tail: Coordinates): Boolean =
        abs(head.x - tail.x) <= 1 && abs(head.y - tail.y) <= 1

    fun moveTail(head: Coordinates, tail: Coordinates): Coordinates {
        return when {
            head.y == tail.y -> when {
                head.x > tail.x -> tail.right()
                head.x < tail.x -> tail.left()
                else -> tail
            }
            head.x == tail.x -> when {
                head.y > tail.y -> tail.up()
                head.y < tail.y -> tail.down()
                else -> tail
            }
            head.x > tail.x && head.y > tail.y -> tail.upRight()
            head.x > tail.x && head.y < tail.y -> tail.downRight()
            head.x < tail.x && head.y > tail.y -> tail.upLeft()
            head.x < tail.x && head.y < tail.y -> tail.downLeft()
            else -> throw IllegalStateException()
        }
    }

    fun getVisitedPositionCount(movements: List<String>): Int {
        val visitedPositions = mutableSetOf<Coordinates>()

        var head = Coordinates(0, 0)
        var tail = Coordinates(0, 0)

        visitedPositions += tail

        movements.forEach {
            val (direction, stepsString) = it.split(" ")
            val steps = stepsString.toInt()

            repeat(steps) {
                head = head.move(direction)
                if (!isTailTouchingHead(head, tail)) {
                    tail = moveTail(head, tail)
                    visitedPositions += tail
                }
            }
        }

        return visitedPositions.size
    }

    val testMovements = readFile("day09_test.txt")

    check(
        getVisitedPositionCount(testMovements) == 13
    )

    val movements = readFileForDay(9)

    println(getVisitedPositionCount(movements))
}

data class Coordinates(
    val x: Int,
    val y: Int,
) {
    fun up() = copy(y = y + 1)
    fun down() = copy(y = y - 1)
    fun left() = copy(x = x - 1)
    fun right() = copy(x = x + 1)

    fun upRight() = copy(x = x + 1, y = y + 1)
    fun upLeft() = copy(x = x - 1, y = y + 1)
    fun downRight() = copy(x = x + 1, y = y - 1)
    fun downLeft() = copy(x = x - 1, y = y - 1)

    fun move(direction: String): Coordinates {
        return when (direction) {
            "U" -> up()
            "D" -> down()
            "L" -> left()
            "R" -> right()
            else -> throw IllegalArgumentException()
        }
    }
}