package day09

import readFile
import readFileForDay
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

        val knots = mutableListOf(
            Coordinates(0, 0),
            Coordinates(0, 0),
            Coordinates(0, 0),
            Coordinates(0, 0),
            Coordinates(0, 0),
            Coordinates(0, 0),
            Coordinates(0, 0),
            Coordinates(0, 0),
            Coordinates(0, 0),
            Coordinates(0, 0),
        )

        visitedPositions += knots.last()

        movements.forEach {
            val (direction, stepsString) = it.split(" ")
            val steps = stepsString.toInt()

            repeat(steps) {
                knots[0] = knots[0].move(direction)
                for (knotIndex in 0 until knots.lastIndex) {
                    val localHead = knots[knotIndex]
                    val localTail = knots[knotIndex + 1]
                    if (!isTailTouchingHead(localHead, localTail)) {
                        knots[knotIndex + 1] = moveTail(localHead, localTail)
                    }
                }
                visitedPositions += knots.last()
            }
        }

        return visitedPositions.size
    }

    val testMovements = readFile("day09_test_2.txt")

    check(
        getVisitedPositionCount(testMovements) == 36
    )

    val movements = readFileForDay(9)

    println(getVisitedPositionCount(movements))
}
