package day12

import day12.Direction.*
import readFileForDay

fun main() {
    val heightmap = readFileForDay(12)

    fun findStart(heightmap: List<String>): Pair<Int, Int> =
        heightmap.run {
            val y = indexOfFirst { it.contains('S') }
            val x = heightmap[y].indexOf('S')
            y to x
        }

    val (startY, startX) = findStart(heightmap)
    println(
        bfs(
            heightmap,
            startY,
            startX,
        )
    )
}

fun bfs(heightmap: List<String>, startY: Int, startX: Int): Int {
    val input = heightmap.map { it.replace('S','a').replace('E', 'z') }

    val distancesFromStart = Array(input.size) { IntArray(input.first().length) { Int.MAX_VALUE } }
    distancesFromStart[startY][startX] = 0

    val visitedCoordinates = mutableSetOf<Pair<Int, Int>>()
    val coordinatesToVisit = mutableListOf(startY to startX)

    val directions = listOf(UP, DOWN, LEFT, RIGHT)

    while (coordinatesToVisit.isNotEmpty()) {
        val (visitingY, visitingX) = coordinatesToVisit.removeFirst()

        directions
            .map { direction -> (visitingY + direction.y) to (visitingX + direction.x) }
            .forEach { (neighborY, neighborX) ->
                val neighborElevation = input.getOrNull(neighborY)?.getOrNull(neighborX) ?: return@forEach
                if (neighborElevation - input[visitingY][visitingX] <= 1) {
                    distancesFromStart[neighborY][neighborX] = minOf(
                        distancesFromStart[neighborY][neighborX],
                        distancesFromStart[visitingY][visitingX] + 1
                    )
                    val neighbor = neighborY to neighborX
                    if (neighbor !in visitedCoordinates && neighbor !in coordinatesToVisit) {
                        coordinatesToVisit += neighbor
                    }
                }
            }

        visitedCoordinates += visitingY to visitingX
    }

    val targetY = heightmap.indexOfFirst { it.contains('E') }
    val targetX = heightmap[targetY].indexOf('E')
    return distancesFromStart[targetY][targetX]
}

sealed class Direction(
    val x: Int,
    val y: Int,
) {
    object UP : Direction(0, 1)
    object DOWN : Direction(0, -1)
    object LEFT : Direction(-1, 0)
    object RIGHT : Direction(1, 0)
}