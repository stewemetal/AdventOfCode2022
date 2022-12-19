package day12

import day12.Direction.*
import readFileForDay

fun main() {
    val heightmap = readFileForDay(12)

    var minSteps = Int.MAX_VALUE

    heightmap.indices.forEach { y ->
        heightmap.indices.forEach { x ->
            if (heightmap[y][x] == 'S' || heightmap[y][x] == 'a') {
                minSteps = minOf(
                    minSteps,
                    bfs(heightmap, y, x)
                )
            }
        }
    }

    println(minSteps)
}